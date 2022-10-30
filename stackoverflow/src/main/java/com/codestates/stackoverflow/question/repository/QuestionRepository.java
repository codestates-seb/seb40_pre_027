package com.codestates.stackoverflow.question.repository;

import com.codestates.stackoverflow.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    //    @Modifying
//    @Query(value = "UPDATE Question q set q.likeCount = q.likeCount + :val where q.questionId = :questionId")
//    public void modifyLikeCount(@Param("questionId") Long questionId, @Param("val") int val);
    @Query(value = "SELECT q FROM Question AS q INNER JOIN q.questionTag qt INNER JOIN t.tag t WHERE t.tagName = :tagName", nativeQuery = true)
    Page<Question> findByTagName(@Param("tagName") String tagName, Pageable pageable);
}
