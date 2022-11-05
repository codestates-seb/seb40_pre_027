package com.codestates.stackoverflow.question.repository;

import com.codestates.stackoverflow.question.entity.QuestionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {
//    @Query("SELECT count(*) FROM QuestionTag qt INNER JOIN qt.tag t " +
//            "WHERE t.tagName = :tagName " +
//            "ORDER BY qt.createdAt DESC")
//    int findNumberOfQuestionsWithTag(@Param(("tagName")) String tagName);
//
//    @Query("select count(*) FROM QuestionTag qt INNER JOIN qt.tag t " +
//            "WHERE t.tagName = :tagName AND qt.createdAt >= :since " +
//            "ORDER BY qt.createdAt DESC")
//    int findNumberOfQuestionsWithTagSince(@Param(("tagName")) String tagName, @Param("since") LocalDateTime since);

    List<QuestionTag> findByQuestionId(Long questionId);
    Optional<QuestionTag> findByQuestionIdAndContent(Long questionId, String content);

    @Transactional
    void deleteByQuestionTagId(Long questionTagId);
}
