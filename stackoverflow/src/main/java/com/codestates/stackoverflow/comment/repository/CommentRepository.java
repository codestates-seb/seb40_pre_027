package com.codestates.stackoverflow.comment.repository;

import com.codestates.stackoverflow.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
