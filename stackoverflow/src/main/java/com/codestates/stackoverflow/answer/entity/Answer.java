package com.codestates.stackoverflow.answer.entity;

import com.codestates.stackoverflow.Reply.entity.Reply;
import com.codestates.stackoverflow.answerLikes.entity.AnswerLikes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false , length = 255)
    private String answerContent;

    @Column
    private Long answerLikesCount;

    @Column
    @CreatedDate
    private LocalDateTime answerCreatedAt;

    @Column
    @LastModifiedDate
    private LocalDateTime answerModifiedAt;

    //Question과의 매핑
    //@ManyToOne
    //@JoinColumn(name = "QUESTION_ID")
    //private Question question;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "Replies")
    private List<Reply> answerReplyComments = new ArrayList<>();



}
