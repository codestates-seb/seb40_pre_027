package com.codestates.stackoverflow.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
public class CommentDto {
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        @NotBlank
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private Long commentId;

        @NotBlank
        private String content;

        public void setCommentId(Long commentId) {
            this.commentId = commentId;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class Response {
        private Long commentId;

        private String content;

        private LocalDateTime createdAt;
    }
}
