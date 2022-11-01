package com.codestates.stackoverflow.question.repository;

import com.codestates.stackoverflow.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    //    @Modifying
//    @Query(value = "UPDATE Question q set q.likeCount = q.likeCount + :val where q.questionId = :questionId")
//    public void modifyLikeCount(@Param("questionId") Long questionId, @Param("val") int val);
    @Query("SELECT q from Question q INNER JOIN q.questionTags qt INNER JOIN qt.tag t WHERE t.tagName = :tagName")
    Page<Question> findByTagName(@Param("tagName") String tagName, Pageable pageable);

    Page<Question> findByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT q FROM Question q INNER JOIN QuestionTag qt INNER JOIN Tag t " +
            "WHERE t.tagName = :tagName " +
            "ORDER BY qt.createdAt DESC")
    Page<Question> findAllWithTag(@Param(("tagName")) String tagName, Pageable pageable);

    @Query("SELECT q FROM Question q INNER JOIN QuestionTag qt INNER JOIN Tag t " +
            "WHERE t.tagName = :tagName AND qt.createdAt >= :since " +
            "ORDER BY qt.createdAt DESC")
    Page<Question> findAllWithTagSince(@Param(("tagName")) String tagName, @Param("since") LocalDateTime since, Pageable pageable);

    @Query("SELECT q FROM Question q INNER JOIN QuestionLikes l ")
    Page<Question> findByOrderByQuestionLikes(Pageable pageable);

//    @Query("SELECT q FROM Question q LEFT JOIN Answer a where q.answers = null " +
//            "WHERE " +
//            "ORDER BY ")
//    Page<Question> findAllWithNoAnswerOrderByLikes(Pageable pageable);


}
