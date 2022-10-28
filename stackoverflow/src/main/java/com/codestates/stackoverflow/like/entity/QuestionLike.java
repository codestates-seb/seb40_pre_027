package com.codestates.stackoverflow.like.entity;

import com.codestates.stackoverflow.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "QUESTION")
public class QuestionLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column
    private int val;


    public static QuestionLike of(Question question, int val) {
        QuestionLike questionLike = new QuestionLike();
        questionLike.setQuestion(question);
        questionLike.setVal(val);
        return questionLike;
    }
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
}
