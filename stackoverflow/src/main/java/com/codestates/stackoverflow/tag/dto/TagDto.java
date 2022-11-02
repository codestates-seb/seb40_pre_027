package com.codestates.stackoverflow.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TagDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {

        private Long tagId;

        private String tagName = "";

        private String tagContent;

        private int askedTotal;

        private int questionsAskedToday;

        private int questionsAskedThisWeek;
    }

    public static class ResponseIcon {

    }
}
