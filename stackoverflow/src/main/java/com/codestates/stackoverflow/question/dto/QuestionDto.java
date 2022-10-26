package com.codestates.stackoverflow.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class QuestionDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private int viewCount = 0;

        private int vote = 0;

        private LocalDateTime createdAt = LocalDateTime.now();

        private LocalDateTime modifiedAt = LocalDateTime.now();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        @NotBlank
        private Long questionId;


        private String title;
        private String content;
        private int vote;

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class Response {
        private long questionId;
        private String title;
        private String content;
        private int viewCount;
        private int vote;

        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
