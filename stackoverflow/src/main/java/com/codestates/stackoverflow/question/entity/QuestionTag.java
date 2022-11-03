package com.codestates.stackoverflow.question.entity;

import com.codestates.stackoverflow.tag.entity.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
public class QuestionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    @JsonBackReference
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TAG_ID")
    @JsonBackReference
    private Tag tag;

    private LocalDateTime createdAt;

    public static QuestionTag of(Question question, Tag tag) {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestion(question);
        questionTag.setTag(tag);
        return questionTag;
    }
}
