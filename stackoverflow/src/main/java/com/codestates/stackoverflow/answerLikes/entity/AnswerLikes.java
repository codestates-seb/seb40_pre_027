package com.codestates.stackoverflow.answerLikes.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column(updatable = false, nullable = true)
    private Long memberId;

    @Column(updatable = false, nullable = false)
    private Long answerId;

    @Column(nullable = false)
    private int val;


    public static AnswerLikes of(Long answerId, Long memberId, int val) {
        AnswerLikes answerLikes = new AnswerLikes();
        answerLikes.setAnswerId(answerId);
        answerLikes.setMemberId(memberId);
        answerLikes.setVal(val);
        return answerLikes;
    }
}
