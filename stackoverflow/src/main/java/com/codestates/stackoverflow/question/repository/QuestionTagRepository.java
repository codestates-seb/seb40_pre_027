package com.codestates.stackoverflow.question.repository;

import com.codestates.stackoverflow.question.entity.QuestionTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {
}
