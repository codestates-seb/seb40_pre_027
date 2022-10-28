package com.codestates.stackoverflow.like.entity;

import com.codestates.stackoverflow.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "QUESTION_LIKE")
public class QuestionLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column(updatable = false, nullable = false)
    private Long memberId;

    @Column(updatable = false, nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private int val;


    public static QuestionLike of(Long questionId, int val) {
        QuestionLike questionLike = new QuestionLike();
        questionLike.setQuestionId(questionId);
        questionLike.setVal(val);
        return questionLike;
    }
}
