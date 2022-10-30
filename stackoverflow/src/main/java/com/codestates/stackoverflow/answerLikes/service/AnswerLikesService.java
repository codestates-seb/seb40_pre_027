package com.codestates.stackoverflow.answerLikes.service;

import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.answer.repository.AnswerRepository;
import com.codestates.stackoverflow.answer.service.AnswerService;
import com.codestates.stackoverflow.answerLikes.entity.AnswerLikes;
import com.codestates.stackoverflow.answerLikes.repository.AnswerLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.codestates.stackoverflow.answerLikes.entity.AnswerLikes.LikesStatus.*;

@Service
public class AnswerLikesService {
    private final AnswerLikesRepository answerLikesRepository;
    private final AnswerRepository answerRepository;
    private final AnswerService answerService;

    @Autowired
    public AnswerLikesService(AnswerLikesRepository answerLikesRepository,
                              AnswerRepository answerRepository,
                              AnswerService answerService) {
        this.answerLikesRepository = answerLikesRepository;
        this.answerRepository = answerRepository;
        this.answerService = answerService;
    }

    public void addLike(Long whoAnswerLikes,
                        Long answerId){
        Answer findAnswer = answerService.findVerifiedAnswer(answerId);
        //NULL 일 경우의 수를 answerService 에 추가할 것 !!
        findAnswer.setAnswerId(answerId);

        AnswerLikes answerLikes = answerLikesRepository.findByAnswerAndWhoAnswerLikeId(findAnswer,whoAnswerLikes);
        
        if(answerLikes.getLikeStatus() == LIKE_CLICK){
            findAnswer.setAnswerLikesCount(findAnswer.getAnswerLikesCount()+1);
            answerLikes.setLikeStatus(NO_CLICK);
        }
        else if(answerLikes.getLikeStatus() == NO_CLICK){
            findAnswer.setAnswerLikesCount(findAnswer.getAnswerLikesCount()+1);
            answerLikes.setLikeStatus(LIKE_CLICK);
        }
        else if(answerLikes.getLikeStatus() == HATE_CLICK){
            findAnswer.setAnswerLikesCount(findAnswer.getAnswerLikesCount()-2);
            answerLikes.setLikeStatus(HATE_CLICK);
        }
        answerRepository.save(findAnswer);
        answerLikesRepository.save(answerLikes);
    }
    public void addHate(Long whoAnswerLikes,
                        Long answerId){
        Answer findAnswer = answerService.findVerifiedAnswer(answerId);
        //NULL 일 경우의 수를 answerService 에 추가할 것 !!
        findAnswer.setAnswerId(answerId);

        AnswerLikes answerLikes = answerLikesRepository.findByAnswerAndWhoAnswerLikeId(findAnswer,whoAnswerLikes);

        if(answerLikes.getLikeStatus() == LIKE_CLICK){
            findAnswer.setAnswerLikesCount(findAnswer.getAnswerLikesCount()-2);
            answerLikes.setLikeStatus(HATE_CLICK);
        }
        else if(answerLikes.getLikeStatus() == NO_CLICK){
            findAnswer.setAnswerLikesCount(findAnswer.getAnswerLikesCount()-1);
            answerLikes.setLikeStatus(HATE_CLICK);

        }
        else if(answerLikes.getLikeStatus() == HATE_CLICK){
            findAnswer.setAnswerLikesCount(findAnswer.getAnswerLikesCount()+1);
            answerLikes.setLikeStatus(NO_CLICK);
        }
        answerRepository.save(findAnswer);
        answerLikesRepository.save(answerLikes);
    }
}
