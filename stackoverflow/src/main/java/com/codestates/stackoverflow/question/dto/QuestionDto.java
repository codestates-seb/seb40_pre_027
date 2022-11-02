package com.codestates.stackoverflow.question.dto;

import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.comment.entity.Comment;
import com.codestates.stackoverflow.tag.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class QuestionDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private String[] tags;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch {
        private Long questionId;

        private String title;

        private String content;

        private String[] tags;

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

        private int likeCount;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        private String[] tags;

        private List<Comment> comments;

        private List<Answer> answers;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class ResponseQuestionsList {

        private String title;

        private String content;

        private Long memberId;

        private String name;

        private int viewCount;

        private int likeCount;

        private int answerCount;

        private List<TagDto.ResponseIcon> tags;

        private int totalCount;
    }
}