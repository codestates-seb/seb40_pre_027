package com.codestates.stackoverflow.member.entity;

import com.codestates.stackoverflow.audit.Auditable;
import com.codestates.stackoverflow.comment.entity.Comment;
import com.codestates.stackoverflow.question.entity.Question;
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

    @Column(length = 50)
    private String location;
    @Column(length = 50)
    private String title;
    @Column(length = 200)
    private String introduction;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    /* 에러 발생 - 주석 처리 */
//    @OneToMany(mappedBy = "member")
//    private List<Question> questions = new ArrayList<>();

//    @OneToMany(mappedBy = "member")
//    private List<Comment> replies = new ArrayList<>();

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
