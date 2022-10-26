package com.codestates.stackoverflow.question.entity;

import com.codestates.stackoverflow.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(length = 150, nullable = false)
    private String title;

    @Column(length = 30000, nullable = false)
    private String content;

//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;

//    @OneToMany(mappedBy = "question")
//    private List<Answer> answers;

    @OneToMany(mappedBy = "question")
    private List<Tag> tags;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int vote;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime modifiedAt;
}
