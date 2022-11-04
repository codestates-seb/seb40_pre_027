package com.codestates.stackoverflow.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class TagDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {

        private Long tagId;

        private String tagName = "";

        private LocalDateTime createdAt;

        private int askedTotal;

        private int questionsAskedToday;

        private int questionsAskedThisWeek;
    }
}
