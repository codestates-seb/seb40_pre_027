package com.codestates.stackoverflow.answer.service;

import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.answer.repository.AnswerRepository;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.service.MemberService;
import com.codestates.stackoverflow.member.service.impl.MemberServiceImpl;
import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import com.codestates.stackoverflow.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final MemberServiceImpl memberServiceImpl;

    //10.30 answer<->question mapping add
    public Answer createAnswer(Answer answer, long questionId){
        Member member = memberServiceImpl.findAuthenticatedMember();
        member.setAnswers(answer);
        Question question = questionService.findValidQuestion(questionId);
        question.setAnswers(answer);
        questionRepository.save(question);
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer){
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
        answerRepository.delete(findAnswer);
    }

    public Answer findAnswer(long answerId) {
        Answer find = findVerifiedAnswer(answerId);
        return answerRepository.findByAnswerId(answerId);
    }
    @Transactional
    public Answer findVerifiedAnswer(long answerId){
        Optional<Answer> optionalAnswer =
                answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(() -> new RuntimeException());
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
