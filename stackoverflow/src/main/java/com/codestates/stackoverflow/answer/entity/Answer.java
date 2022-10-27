package com.codestates.stackoverflow.answer.entity;

import com.codestates.stackoverflow.Reply.entity.Reply;
import com.codestates.stackoverflow.answerLikes.entity.AnswerLikes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

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

    //수정 및 삭제 권한은 answerWriterId로..?
    //@ManyToOne
    //@JoinColumn(name = "MEMBER_ID")
    //private Member answerWriter;

    @Column(nullable = false , length = 255)
    private String answerContent;

    //각 앤서 하나당, 좋아요 카운트가 존재합니다. Default 는 0이며, AnswerLikes 를 통해 수정이 가능합니다.
    @Column
    private Long answerLikesCount;

    @Column
    @CreatedDate
    private LocalDateTime answerCreatedAt;

    @Column
    @CreatedDate
    private LocalDateTime answerModifiedAt;

    //answerLikes
    @OneToOne(cascade = {CascadeType.ALL})
    private AnswerLikes answerLikes;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "Replies")
    private List<Reply> answerReplyComments = new ArrayList<>();

}
