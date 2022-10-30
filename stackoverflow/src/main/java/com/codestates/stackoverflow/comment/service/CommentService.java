package com.codestates.stackoverflow.comment.service;

import com.codestates.stackoverflow.comment.entity.Comment;
import com.codestates.stackoverflow.comment.repository.CommentRepository;
import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import com.codestates.stackoverflow.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    public Comment createComment(Long questionId, Comment comment) {
        Question question = questionService.findValidQuestion(questionId);
        question.setComments(comment);
        questionRepository.save(question);

        return commentRepository.save(comment);
    }

    /**
     * 댓글 내용 공백 처리 어떻게 할 것인지 추후 논의 필요
     * 작성한 지 5분이 지나지 않은 경우 수정 가능
     */
    public Comment updateComment(Comment comment) {
        Comment findComment = findValidComment(comment.getCommentId());

        LocalDateTime createdAt = findComment.getCreatedAt();
        LocalDateTime modifiedAt = LocalDateTime.now();

        if(Duration.between(createdAt, modifiedAt).getSeconds() > 300) {
            throw new RuntimeException();
        } else {
            Optional.ofNullable(comment.getContent())
                    .ifPresent(findComment::setContent);
        }

        return commentRepository.save(findComment);
    }

    /**
     * 1 : N 조회 기능 구현 필요
     */
    public Page<Comment> findComments(Long questionId, Pageable pageable) {
        Question findQuestion = questionService.findValidQuestion(questionId);
        return commentRepository.findByQuestion(findQuestion, pageable);
    }

    public void deleteComment(Long commentId) {
        Comment findComment = findValidComment(commentId);
        commentRepository.delete(findComment);
    }

    @Transactional(readOnly = true)
    public Comment findValidComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        return optionalComment.orElseThrow(RuntimeException::new);
    }
}
