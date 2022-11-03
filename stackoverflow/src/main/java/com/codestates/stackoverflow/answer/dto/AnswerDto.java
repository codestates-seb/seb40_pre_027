package com.codestates.stackoverflow.answer.dto;

import com.codestates.stackoverflow.Reply.entity.Reply;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class AnswerDto {
    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{

        @NotBlank(message = "내용을 필수로 입력해야 합니다.")
        private String answerContent;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch{

        private long answerId;

        @NotBlank(message = "내용을 필수로 입력해야 합니다.")
        private String answerContent;

        public void setAnswerId(Long answerId){
            this.answerId = answerId;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class pick{
        private long answerId;

        private long bestAnswer;
        public void setAnswerId(Long answerId){
            this.answerId = answerId;
        }

    }
    @Getter
    @AllArgsConstructor
    @Setter
    @NoArgsConstructor
    @Builder
    public static class Response{

        private long answerId;

        private String answerContent;

        private long answerLikesCount;

        private LocalDateTime answerCreatedAt;

        private LocalDateTime answerModifiedAt;

        private List<Reply> replies;
    }
}
