package com.codestates.stackoverflow.question.entity;

import javax.persistence.Embeddable;

@Embeddable
public enum ActiveType {
    ASKED, ANSWERED, MODIFIED
}
