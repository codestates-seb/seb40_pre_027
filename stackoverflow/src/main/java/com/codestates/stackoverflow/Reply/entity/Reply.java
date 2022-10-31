package com.codestates.stackoverflow.Reply.entity;

import com.codestates.stackoverflow.answer.entity.Answer;
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
    private LocalDateTime replyCreatedAt;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "answer_id")
    private Answer answer;

}
