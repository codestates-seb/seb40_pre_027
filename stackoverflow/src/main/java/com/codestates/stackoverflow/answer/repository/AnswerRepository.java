package com.codestates.stackoverflow.answer.repository;

import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.comment.entity.Comment;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    Answer findByAnswerId(long answerId);
    Page<Answer> findByQuestion(Question question, Pageable pageable);

    Answer findByQuestionAndBestAnswer(@Param("Question")Question question, @Param("BestAnswer")long bestAnswer);

//    @Query("SELECT a FROM Answer a INNER JOIN Question q WHERE q.questionId = :questionId AND a.bestAnswer = :bestAnswer")
//    Answer findByQuestionAndBestAnswer(@Param("questionId") Long questionId, @Param("bestAnswer") long bestAnswer);

}
