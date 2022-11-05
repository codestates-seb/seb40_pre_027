package com.codestates.stackoverflow.question.entity;

import com.codestates.stackoverflow.tag.entity.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter @Setter
@Entity
@NoArgsConstructor
public class QuestionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionTagId;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private Long tagId;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "QUESTION_ID")
//    Question question;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "TAG_ID")
//    Tag tag;

    @Column(nullable = false)
    private String content;

    public QuestionTag(Long questionId, Long tagId, String content) {
        this.questionId = questionId;
        this.tagId = tagId;
        this.content = content;
    }
}
