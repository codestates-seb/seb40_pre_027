package com.codestates.stackoverflow.answerLikes.dto;

import com.codestates.stackoverflow.answerLikes.entity.AnswerLikes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AnswerLikesDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class patch{
        private Long AnswerLikesId;
        private Long answerId;
        private Long whoAnswerLikeId;
    }
}
