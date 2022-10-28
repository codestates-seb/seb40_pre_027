package com.codestates.stackoverflow.tag;

import com.codestates.stackoverflow.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(length = 35, nullable = false, unique = true)
    private String content;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
}
