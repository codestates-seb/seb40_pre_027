package com.codestates.stackoverflow.question.service;

import com.codestates.stackoverflow.answer.repository.AnswerRepository;
import com.codestates.stackoverflow.comment.repository.CommentRepository;
import com.codestates.stackoverflow.exception.BusinessLogicException;
import com.codestates.stackoverflow.exception.ExceptionCode;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.repository.MemberRepository;
import com.codestates.stackoverflow.member.service.impl.MemberServiceImpl;
import com.codestates.stackoverflow.question.entity.ActiveInfo;
import com.codestates.stackoverflow.question.entity.ActiveType;
import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.entity.QuestionTag;
import com.codestates.stackoverflow.question.mapper.QuestionMapperImpl;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import com.codestates.stackoverflow.question.repository.QuestionTagRepository;
import com.codestates.stackoverflow.tag.entity.Tag;
import com.codestates.stackoverflow.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapperImpl mapper;
    private final TagService tagService;
    private final MemberServiceImpl memberServiceImpl;
    private final MemberRepository memberRepository;
    private final HibernateSearchService searchService;

    public Question createQuestion(Question question) {
        //tagContent(String ??????)??? ????????? Tag ????????? ???????????? ????????????.
        log.info("[createQuestion] ?????? ??? ?????? : " + Arrays.toString(question.getTags()));
        mapAndSaveTags(question);

        Member authMember = memberServiceImpl.findAuthenticatedMember();
        authMember.setQuestions(question);
        Member writer = memberRepository.save(authMember);
        question.setMember(writer);

        ActiveInfo activeInfo = new ActiveInfo(writer.getMemberId(), question.getCreatedAt(), ActiveType.ASKED);
        question.setActiveInfo(activeInfo);

        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question) {
        Question findQuestion = findValidQuestion(question.getQuestionId());

        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getContent())
                .ifPresent(findQuestion::setContent);
        mapAndSaveTags(question);
        findQuestion.setModifiedAt(LocalDateTime.now());

        Member writer = findQuestion.getMember();
        ActiveInfo activeInfo = new ActiveInfo(writer.getMemberId(), findQuestion.getModifiedAt(), ActiveType.MODIFIED);
        question.setActiveInfo(activeInfo);

        return questionRepository.save(findQuestion);
    }

    public Question findQuestion(Long questionId) {
        Question findQuestion = findValidQuestion(questionId);
        //????????? 1 ??????
        findQuestion.setViewCount(findQuestion.getViewCount() + 1);

        mapper.questionToQuestionResponse(findQuestion);

        return questionRepository.save(findQuestion);
    }

    @Transactional(readOnly = true)
    public List<Question> searchQuestions(@PathVariable("keyword") String keyword,
                                          @RequestParam int page,
                                          @RequestParam int size) throws BusinessLogicException {
        log.info("[searchQuestions] keyword = " + keyword);
        if(keyword.matches(".*[???-??????-??????-???]+.*") && keyword.startsWith("\"") && keyword.endsWith("\"")) {
            keyword = keyword.substring(1, keyword.length() - 1);
        }

        return questionRepository.findByKeyword(keyword, PageRequest.of(page, size, Sort.by("questionId").descending()))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND)).getContent();

//        return searchService.searchQuestions(keyword);
    }

    @Transactional(readOnly = true)
    public Page<Question> findQuestions(String tab, int page, int size) {
        if (tab == null) tab = "Newest";

        switch (tab) {
            case "newest":
                return questionRepository.findByOrderByCreatedAtDesc(PageRequest.of(page, size));

            case "active":
                Sort sort = Sort.by("activeInfo.lastActiveAt").descending();

                return questionRepository.findAll(PageRequest.of(page, size, sort));

            case "Unanswered":
                return null;
            case "Frequent":
                return null;
            case "Score":
                return null;
        }
        return null;
    }

    /**
     * tag??? null????????? ??? ?????? ???????????? ?????? ?????? ??? ??????
     */
    @Transactional(readOnly = true)
    public Page<Question> findTaggedQuestions(String tagName, String tab, Integer page, Integer size) {
        //tag??? tagName??? ????????? ?????? ?????????
        log.info("[findTaggedQuestions ??????]: Tag = " + tagName);
        if (tab == null) tab = "Newest";
        if (page == null) page = 0;
        if(size == null) size = 30;

        switch (tab) {
            case "Newest":
                return questionRepository.findByTagName(tagName, PageRequest.of(page, 30,
                        Sort.by("questionId").descending()));
            case "Active":
                return questionRepository.findByTagName(tagName, PageRequest.of(page, size,
                        Sort.by("activeInfo.lastActiveAt").descending()));
            case "Bountied":
                return null;
            case "Unanswered":
                return null;
            case "Frequent":
                return null;
            case "Score":
                return null;
        }
        return null;
    }

    public void deleteQuestion(long questionId) {
        Question findQuestion = findValidQuestion(questionId);
        questionRepository.delete(findQuestion);
    }

    public int modifyLikeCount(Long questionId, int val) {
        Question findQuestion = findValidQuestion(questionId);
        int newLikeCount = findQuestion.getLikeCount() + val;
        findQuestion.setLikeCount(newLikeCount);

        return questionRepository.save(findQuestion).getLikeCount();
    }

    public void mapAndSaveTags(Question question) {
        Optional.ofNullable(question.getTags())
                .ifPresent(tagNames -> {
                    List<Tag> tags = tagService.tagNameArrayToTagList(tagNames);
//                    mapQuestionAndTags(question, tags);
                    tags.stream().forEach(tag -> {
                        QuestionTag questionTag = QuestionTag.of(question, tag);
                        question.setQuestionTags(questionTag);
                        tag.setQuestionTags(questionTag);
                    });
                    tagService.saveTags(tags);
                });
    }

    @Transactional(readOnly = true)
    public Question findValidQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        return optionalQuestion.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }
}
