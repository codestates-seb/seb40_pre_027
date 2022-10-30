package com.codestates.stackoverflow.question.entity;

import com.codestates.stackoverflow.tag.entity.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class QuestionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionTagId;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public static QuestionTag of(Question question, Tag tag) {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestion(question);
        questionTag.setTag(tag);
        return questionTag;
    }
}
