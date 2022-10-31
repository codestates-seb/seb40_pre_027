package com.codestates.stackoverflow.Reply.repository;

import com.codestates.stackoverflow.Reply.entity.Reply;
import com.codestates.stackoverflow.answer.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository <Reply,Long> {
    Page<Reply> findByAnswer(Answer answer, Pageable pageable);


}
