package com.codestates.stackoverflow.question.entity;


import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.comment.entity.Comment;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.tag.entity.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@Indexed
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(length = 150, nullable = false)
    @Field
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Field
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private Member member;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Answer> answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<QuestionTag> questionTags = new ArrayList<>();


    private String[] tags;



//    public void setTags(Tag tag) {
//        this.tags.add(tag);
//        tag.setQuestion(this);
//    }


//    @OneToMany(mappedBy = "question")
//    private List<QuestionLike> likes = new ArrayList<>();

    @Column(nullable = false)
    private int viewCount = 0;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd : HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd : HH:mm:ss")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Embedded
    private ActiveInfo activeInfo;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    //== 연관관계 편의 메서드==//
    // 연관관계 한 번에 정리
    public void setComments(Comment comment) {
        this.comments.add(comment);
        comment.setQuestion(this);
    }

    public void setAnswers(Answer answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }

    public void setQuestionTags(QuestionTag questionTag) {
        this.questionTags.add(questionTag);
        questionTag.setQuestion(this);
    }
}
