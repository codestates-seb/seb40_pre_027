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
    @Query("SELECT q FROM Question q WHERE lower(q.title) like lower(CONCAT('%', :keyword, '%')) OR q.content like lower(CONCAT('%', :keyword, '%'))")
    Optional<Page<Question>> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Page<Question> findByOrderByCreatedAtDesc(Pageable pageable);

    //theta join을 활용해 연관관계가 없는 테이블을 join
    @Query("SELECT q FROM Question q, QuestionTag qt WHERE q.questionId = qt.questionId AND qt.content = :tagName")
    Page<Question> findByTagName(@Param("tagName") String tagName, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Question q")
    int countAllQuestions();
}
