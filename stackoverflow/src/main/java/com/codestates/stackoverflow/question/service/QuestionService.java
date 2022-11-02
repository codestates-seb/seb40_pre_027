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
//    private final QuestionTagRepository questionTagRepository;
//    private final AnswerRepository answerRepository;
//    private final CommentRepository commentRepository;
    private final TagService tagService;
    private final MemberServiceImpl memberServiceImpl;
    private final MemberRepository memberRepository;

    public Question createQuestion(Question question) {
        //tagContent(String 타입)의 배열을 Tag 객체의 리스트로 변경한다.
        log.info("[createQuestion] 매핑 전 태그 : " + Arrays.toString(question.getTags()));
        mapAndSaveTags(question);

        Member authMember = memberServiceImpl.findAuthenticatedMember();
        authMember.setQuestions(question); // 수정
        Member writer = memberRepository.save(authMember); // a
        question.setMember(writer);// cascade 삭제해도 무방

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
        findQuestion.setViewCount(findQuestion.getViewCount() + 1);

        return questionRepository.save(findQuestion);
    }

    @Transactional(readOnly = true)
    public Page<Question> searchQuestions(String keyword, int page, int size) {
        return questionRepository.findByKeyword(keyword, PageRequest.of(page, size))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Page<Question> findQuestions(String tab, int page, int size) {
        if (tab == null) tab = "Newest";

        switch (tab) {
            case "Newest":
                return questionRepository.findByOrderByCreatedAtDesc(PageRequest.of(page, size));

            case "Active":
                Sort sort = Sort.by("activeInfo.lastActiveAt").descending();

                return questionRepository.findAll(PageRequest.of(page, size, sort));

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

    /**
     * tag가 null이거나 빈 경우 필요한지 추후 검토 후 수정
     */
    @Transactional(readOnly = true)
    public Page<Question> findTaggedQuestions(String tagName, String tab, Integer page, Integer size) {
        //tag의 tagName이 동일한 경우 페이지
        log.info("[findTaggedQuestions 작동]: Tag = " + tagName);
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

    /**
     * Bounty 기능 추가시 사용할 메서드
     */
//    public Question addBounty(Question question, int bounty) {
//        question.setBounty(question.getBounty() + bounty);
//        return question;
//    }

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
