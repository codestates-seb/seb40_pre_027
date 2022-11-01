package com.codestates.stackoverflow.Reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ReplyDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class post{

        @NotBlank(message = "내용을 필수로 입력해야 합니다.")
        private String replyContent;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class patch{

        private Long replyId;
        @NotBlank(message = "내용을 필수로 입력해야 합니다.")
        private String replyContent;

//        public void setAnswerId(Long answerId){
//            this.replyId = replyId;
//        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class response{

        private Long replyId;
        private String replyContent;
        private LocalDateTime replyCreatedAt;
    }
}
