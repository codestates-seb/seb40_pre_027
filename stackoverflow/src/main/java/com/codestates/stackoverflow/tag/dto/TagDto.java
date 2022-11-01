package com.codestates.stackoverflow.tag.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TagDto {
    public static class Response {

        private String tagName = "";

//    @Column(columnDefinition = "TEXT")
//    private String tagContent;

        private int askedTotal;

        private int questionsAskedToday;

        private int questionsAskedThisWeek;
    }
}
