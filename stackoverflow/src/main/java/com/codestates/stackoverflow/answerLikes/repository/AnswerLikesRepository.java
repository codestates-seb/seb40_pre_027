package com.codestates.stackoverflow.answerLikes.repository;

import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.answerLikes.entity.AnswerLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerLikesRepository extends JpaRepository<AnswerLikes,Long> {
    AnswerLikes findByAnswerAndWhoAnswerLikeId(Answer answer, long WhoAnswerLikesId);

}
