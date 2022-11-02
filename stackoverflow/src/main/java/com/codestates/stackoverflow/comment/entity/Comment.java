package com.codestates.stackoverflow.comment.entity;

import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    /**
     * stackoverflow comment length 확인하고 추후 수정
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
}
