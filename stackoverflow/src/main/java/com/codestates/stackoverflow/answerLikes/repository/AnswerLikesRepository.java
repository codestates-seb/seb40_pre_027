package com.codestates.stackoverflow.answerLikes.repository;

import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.answerLikes.entity.AnswerLikes;
import com.codestates.stackoverflow.questionLikes.entity.QuestionLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnswerLikesRepository extends JpaRepository<AnswerLikes,Long> {

    Optional<AnswerLikes> findByAnswerIdAndMemberId(Long answerId, Long memberId);

    @Modifying
    @Query("DELETE from AnswerLikes l where l.answerId = :answerId")
    void deleteByAnswer_Id(@Param("answerId") Long answerId);

    @Modifying
    @Query("UPDATE AnswerLikes l set l.val = :val where l.answerId = :answerId")
    void changeLikeVal(@Param("answerId") Long answerId, @Param("val") int val);
}
