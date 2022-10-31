package com.codestates.stackoverflow.answerLikes.service;

import com.codestates.stackoverflow.answer.repository.AnswerRepository;
import com.codestates.stackoverflow.answer.service.AnswerService;
import com.codestates.stackoverflow.answerLikes.entity.AnswerLikes;
import com.codestates.stackoverflow.answerLikes.repository.AnswerLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

    public long saveLike(Long answerId, int val) {
        Optional<AnswerLikes> findAnswerLikes = answerLikesRepository.findByAnswerId(answerId);
        if (findAnswerLikes.isEmpty()) {
            return createLike(answerId, val);
        } else {
            return updateLike(findAnswerLikes.get(), answerId, val);
        }
    }

    public long createLike(Long answerId, int val) {
        answerRepository.findById(answerId).orElseThrow(RuntimeException::new);

        AnswerLikes answerLikes = AnswerLikes.of(answerId, val);
        answerLikesRepository.save(answerLikes);

        return answerService.modifyLikeCount(answerId, val);
    }

    public long updateLike(AnswerLikes answerLikes, Long answerId, int val) {
        if(answerLikes.getVal() == val) {
            answerLikesRepository.deleteByAnswer_Id(answerId);

            return answerService.modifyLikeCount(answerId, -1);
        } else {
            answerLikesRepository.changeLikeVal(answerId, val);

            if (val == 1)
                return answerService.modifyLikeCount(answerId, 2);
            else
                return answerService.modifyLikeCount(answerId, -2);
        }
    }
}
