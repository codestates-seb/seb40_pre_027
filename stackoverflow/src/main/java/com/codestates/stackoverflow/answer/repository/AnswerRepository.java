package com.codestates.stackoverflow.answer.repository;

import com.codestates.stackoverflow.answer.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    Page<Answer> findAllByOrderByAnswerIdDesc(Pageable pageable);
    Answer findByAnswerId(long answerId);
}
