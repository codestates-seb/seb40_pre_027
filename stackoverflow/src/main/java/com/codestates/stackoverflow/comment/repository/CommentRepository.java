package com.codestates.stackoverflow.comment.repository;

import com.codestates.stackoverflow.comment.entity.Comment;
import com.codestates.stackoverflow.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByQuestion(Question question, Pageable pageable);
}
