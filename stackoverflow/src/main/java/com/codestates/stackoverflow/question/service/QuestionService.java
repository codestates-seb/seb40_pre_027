package com.codestates.stackoverflow.question.service;

import com.codestates.stackoverflow.exception.BusinessLogicException;
import com.codestates.stackoverflow.exception.ExceptionCode;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.repository.MemberRepository;
import com.codestates.stackoverflow.member.service.impl.MemberServiceImpl;
import com.codestates.stackoverflow.question.entity.ActiveInfo;
import com.codestates.stackoverflow.question.entity.ActiveType;
import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.entity.QuestionTag;
import com.codestates.stackoverflow.question.mapper.QuestionMapper;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
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
import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper mapper;
    private final TagService tagService;
    private final MemberServiceImpl memberServiceImpl;
    private final MemberRepository memberRepository;

    public Question createQuestion(Question question) {
        Member authMember = memberServiceImpl.findAuthenticatedMember();
        authMember.setQuestions(question);
        question.setMember(authMember);
        Member writer = memberRepository.save(authMember);

        ActiveInfo activeInfo = new ActiveInfo(writer.getMemberId(), question.getCreatedAt(), ActiveType.ASKED);
        question.setActiveInfo(activeInfo);

        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question) {
        Question findQuestion = findValidQuestion(question.getQuestionId());

        Member authMember = memberServiceImpl.findAuthenticatedMember();
        Member writer = findQuestion.getMember();

        if(!Objects.equals(authMember.getMemberId(), writer.getMemberId())) {
            throw new BusinessLogicException(ExceptionCode.NOT_WRITER);
        }

        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getContent())
                .ifPresent(findQuestion::setContent);
        findQuestion.setModifiedAt(LocalDateTime.now());

        ActiveInfo activeInfo = new ActiveInfo(writer.getMemberId(), findQuestion.getModifiedAt(), ActiveType.MODIFIED);
        question.setActiveInfo(activeInfo);

        return questionRepository.save(findQuestion);
    }

    public Question findQuestion(Long questionId) {
        Question findQuestion = findValidQuestion(questionId);
        //조회수 1 증가
        findQuestion.setViewCount(findQuestion.getViewCount() + 1);

        mapper.questionToQuestionResponse(findQuestion);

        return questionRepository.save(findQuestion);
    }

    @Transactional(readOnly = true)
    public Page<Question> findQuestions(String tab, int page, int size) {
        try {
            if (tab == null) tab = "Newest";

            switch (tab) {
                case "newest":
                    return questionRepository.findByOrderByCreatedAtDesc(PageRequest.of(page, size));

                case "active":
                    Sort sort = Sort.by("activeInfo.lastActiveAt").descending();

                    return questionRepository.findAll(PageRequest.of(page, size, sort));
            }
            return null;
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public List<Question> searchQuestions(@PathVariable("keyword") String keyword,
                                          @RequestParam int page,
                                          @RequestParam int size) throws BusinessLogicException {
        log.info("[searchQuestions] keyword = " + keyword);
        if(keyword.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*") && keyword.startsWith("\"") && keyword.endsWith("\"")) {
            keyword = keyword.substring(1, keyword.length() - 1);
        }

        return questionRepository.findByKeyword(keyword, PageRequest.of(page, size, Sort.by("questionId").descending()))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND)).getContent();
    }

//    @Transactional(readOnly = true)
//    public Page<Question> findTaggedQuestions(String tagName, String tab, Integer page, Integer size) {
//        //tag의 tagName이 동일한 경우 페이지
//        log.info("[findTaggedQuestions 작동]: Tag = " + tagName);
//        if (tab == null) tab = "Newest";
//        if (page == null) page = 0;
//        if(size == null) size = 30;
//
//        switch (tab) {
//            case "Newest":
//                return questionRepository.findByTagName(tagName, PageRequest.of(page, 30,
//                        Sort.by("questionId").descending()));
//            case "Active":
//                return questionRepository.findByTagName(tagName, PageRequest.of(page, size,
//                        Sort.by("activeInfo.lastActiveAt").descending()));
//        }
//        return null;
//    }

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

    @Transactional(readOnly = true)
    public Question findValidQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        return optionalQuestion.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }
}
