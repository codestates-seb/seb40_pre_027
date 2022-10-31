package com.codestates.stackoverflow.questionLikes.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table
public class QuestionLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column(updatable = false, nullable = false)
    private Long memberId;

    @Column(updatable = false, nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private int val;

    public static QuestionLikes of(Long questionId, int val) {
        QuestionLikes questionLikes = new QuestionLikes();
        questionLikes.setQuestionId(questionId);
        questionLikes.setVal(val);
        return questionLikes;
    }
}
