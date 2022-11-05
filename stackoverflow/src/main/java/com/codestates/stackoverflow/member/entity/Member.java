package com.codestates.stackoverflow.member.entity;

import com.codestates.stackoverflow.Reply.entity.Reply;
import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.audit.Auditable;
import com.codestates.stackoverflow.auth.RefreshToken;
import com.codestates.stackoverflow.comment.entity.Comment;
//import com.codestates.stackoverflow.image.entity.v2.Image;
//import com.codestates.stackoverflow.image.entity.v2.Image;
import com.codestates.stackoverflow.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member extends Auditable {
    // 이미지 기능 추가해야 함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    // 프로필
    @Column(length = 50)
    private String location;
    @Column(length = 50)
    private String title;
    @Column(length = 200)
    private String introduction;

    // 질문 영역

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    @JsonManagedReference
    private List<Question> questions;
    public void setQuestions(Question question) {
        this.questions.add(question);
        question.setMember(this);
    }

    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "answerWriter")
    @ToString.Exclude
    @JsonManagedReference
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    public void setAnswers(Answer answer) {
        this.answers.add(answer);
        answer.setAnswerWriter(this);
    }
    public void setComments(Comment comment) {
        this.comments.add(comment);
        comment.setMember(this);
    }

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "replyWriter")
    @ToString.Exclude
    @JsonManagedReference
    private List<Reply> replies = new ArrayList<>();

    public void setReplies(Reply reply) {
        this.replies.add(reply);
        reply.setReplyWriter(this);
    }

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @ElementCollection()
    private List<String> roles = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private RefreshToken refreshToken;

    public void setRefreshToken (RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
        if (refreshToken.getMember() != this) {
            refreshToken.setMember(this);
        }
    }

    public static enum MemberStatus {
        MEMBER_ACTIVE("활동 중"),
        MEMBER_SLEEP("휴먼 상태"),
        MEMBER_QUIT("탈퇴 상태");
        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
    public enum MemberRole {
        ROLE_USER,
        ROLE_ADMIN

    }
}
