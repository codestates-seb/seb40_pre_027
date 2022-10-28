package com.codestates.stackoverflow.answer.service;

import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.answer.repository.AnswerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    //private final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }

    public Answer createAnswer(Answer answer){
        //member 의 setter 메서드로 누가 썼는지 지정해주는 기능을 넣어 주어야 한다.
        answer.setAnswerCreatedAt(LocalDateTime.now());
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer){
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());
        Optional.ofNullable(answer.getAnswerContent())
                .ifPresent(content -> findAnswer.setAnswerContent(content));
        findAnswer.setAnswerModifiedAt(LocalDateTime.now());
        return answerRepository.save(findAnswer);
    }

    public Page<Answer> getAnswers(int page , int size){
        PageRequest pageRequest = PageRequest.of(page,size);
        return answerRepository.findAllByOrderByAnswerIdDesc(pageRequest);
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
}
