package com.codestates.stackoverflow.answer.service;

import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.answer.repository.AnswerRepository;
import com.codestates.stackoverflow.exception.BusinessLogicException;
import com.codestates.stackoverflow.exception.ExceptionCode;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.repository.MemberRepository;
import com.codestates.stackoverflow.member.service.impl.MemberServiceImpl;
import com.codestates.stackoverflow.question.entity.ActiveInfo;
import com.codestates.stackoverflow.question.entity.ActiveType;
import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import com.codestates.stackoverflow.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final MemberServiceImpl memberServiceImpl;
    private final MemberRepository memberRepository;

    //10.30 answer<->question mapping add
    public Answer createAnswer(Answer answer, long questionId){
        Question question = questionService.findValidQuestion(questionId);
        question.setAnswers(answer);
        Member member = memberServiceImpl.findAuthenticatedMember();
        member.setAnswers(answer);

        ActiveInfo activeInfo = new ActiveInfo(member.getMemberId(), answer.getAnswerCreatedAt(), ActiveType.ANSWERED);
        question.setActiveInfo(activeInfo);

        memberRepository.save(member);
        questionRepository.save(question);
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer){
        long writerId = answer.getAnswerWriter().getMemberId();
        long patchMemberId = memberServiceImpl.findAuthenticatedMember().getMemberId();

        if(writerId != patchMemberId) {
            new BusinessLogicException(ExceptionCode.NOT_WRITER);
        }

        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());

        Optional.ofNullable(answer.getAnswerContent())
                .ifPresent(content -> findAnswer.setAnswerContent(content));

        findAnswer.setAnswerModifiedAt(LocalDateTime.now());

        return answerRepository.save(findAnswer);
    }

    public Page<Answer> getAnswers(long questionId, Pageable pageable){
        Question findQuestion = questionService.findValidQuestion(questionId);

        return answerRepository.findByQuestion(findQuestion,pageable);
    }

    public void deleteAnswer(Long answerId){
        Answer findAnswer = findVerifiedAnswer(answerId);

        long writerId = findAnswer.getAnswerWriter().getMemberId();
        long deleteMemberId = memberServiceImpl.findAuthenticatedMember().getMemberId();

        if(writerId != deleteMemberId){
            new BusinessLogicException(ExceptionCode.NOT_WRITER);
        }

        answerRepository.delete(findAnswer);
    }

    @Transactional
    public Answer findVerifiedAnswer(long answerId){
        Optional<Answer> optionalAnswer =
                answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        return findAnswer;
    }

    public long modifyLikeCount(Long answerId, int val) {
        Answer findAnswer = findVerifiedAnswer(answerId);
        long newLikeCount = findAnswer.getAnswerLikesCount() + val;
        findAnswer.setAnswerLikesCount(newLikeCount);

        return answerRepository.save(findAnswer).getAnswerLikesCount();
    }

//    public void bestAnswer(long questionId, long answerId){
//        Question question = questionService.findValidQuestion(questionId);
//        if(answerRepository.findByQuestionAndBestAnswer(question,1) == null){
//            new RuntimeException();
//        }
//        else{
//            Answer findAnswer = findVerifiedAnswer(answerId);
//            findAnswer.setBestAnswer(1);
//            answerRepository.save(findAnswer);
//        }
//    }
}
