package com.codestates.stackoverflow.comment.controller;

import com.codestates.stackoverflow.comment.dto.CommentDto;
import com.codestates.stackoverflow.comment.entity.Comment;
import com.codestates.stackoverflow.comment.mapper.CommentMapper;
import com.codestates.stackoverflow.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/reply")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper mapper;

    @PostMapping("{question-id}")
    public ResponseEntity postComment(@PathVariable("question-id") Long questionId,
                                      @Valid @RequestBody CommentDto.Post requestBody) {
        Comment comment = commentService.createComment(mapper.commentPostToComment(requestBody));

        return new ResponseEntity<>(
                mapper.commentToCommentResponse(comment),
                HttpStatus.CREATED);
    }

    @PatchMapping("{comment-id}")
    public ResponseEntity patchComment(@PathVariable("comment-id") Long commentId,
                                       @Valid @RequestBody CommentDto.Patch requestBody) {
        requestBody.setCommentId(commentId);
        Comment comment = commentService.updateComment(mapper.commentPatchToComment(requestBody));

        return new ResponseEntity<>(
                mapper.commentToCommentResponse(comment),
                HttpStatus.OK);
    }

    @GetMapping("{question-id}")
    public ResponseEntity getCommentsOfQuestion(@PathVariable("comment-id") Long questionId,
                                      @Positive @RequestParam int page,
                                      @Positive @RequestParam int size) {
        Page<Comment> pageComments = commentService.findComments(questionId, page, size);
        List<Comment> comments = pageComments.getContent();

        return new ResponseEntity<>(
                mapper.commentsToCommentResponses(comments),
                HttpStatus.OK);
    }

    @DeleteMapping("{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") Long commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
