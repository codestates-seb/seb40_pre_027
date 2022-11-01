package com.codestates.stackoverflow.Reply.entity;

import com.codestates.stackoverflow.answer.entity.Answer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(length = 300)
    private String replyContent;

    @Column
    @CreatedDate
    private LocalDateTime replyCreatedAt=LocalDateTime.now();

    @Column
    @CreatedDate
    private LocalDateTime replyModifiedAt=LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "answer_id")
    @JsonBackReference
    private Answer answer;

}
