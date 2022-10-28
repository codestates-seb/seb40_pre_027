package com.codestates.stackoverflow.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
public class CommentDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        @NotBlank
        private String content;
    }

    public static class Patch {
        private Long commentId;

        @NotBlank
        private String content;

        public void setCommentId(Long commentId) {
            this.commentId = commentId;
        }
    }

    public static class Response {
        private Long commentId;

        private String content;

        private LocalDateTime createdAt;
    }
}
