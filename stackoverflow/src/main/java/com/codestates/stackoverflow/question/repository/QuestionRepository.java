package com.codestates.stackoverflow.question.repository;

import com.codestates.stackoverflow.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Modifying
    @Query(value = "UPDATE Question q set q.likeCount = q.likeCount + :val where q.questionId = :questionId", nativeQuery = true)
    public void modifyLikeCount(@Param("questionId") Long questionId, @Param("val") int val);
}
