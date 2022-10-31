package com.codestates.stackoverflow.question.repository;

import com.codestates.stackoverflow.question.entity.QuestionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {
    @Query("SELECT q FROM Question q INNER JOIN QuestionTag qt INNER JOIN Tag t" +
            "WHERE t.tagName = :tagName" +
            "ORDER BY qt.createdAt DESC")
    int findNumberOfQuestionsWithTag(@Param(("tagName")) String tagName);

    @Query("select count(*) FROM QuestionTag qt INNER JOIN Tag t" +
            "WHERE t.tagName = :tagName AND qt.createdAt >= since" +
            "ORDER BY qt.createdAt DESC")
    int findNumberOfQuestionsWithTagSince(@Param(("tagName")) String tagName, @Param("since") LocalDateTime since);
}
