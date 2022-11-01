package com.codestates.stackoverflow.tag.entity;

import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.entity.QuestionTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(length = 35, nullable = false, unique = true)
    private String tagName;

//    @Column
//    private String tagExplanation;
    @Column
    private int questionsAskedToday;

    @Column
    private int questionsAskedThisWeek;

    @OneToMany(mappedBy = "tag")
    private List<QuestionTag> questionTags = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    public static Tag of(String string) {
        Tag tag = new Tag();
        tag.setTagName(string);
        return tag;
    }

    //== 연관관계 편의 메서드==//
    // 연관관계 한 번에 정리
    public void setQuestionTags(QuestionTag questionTag) {
        this.questionTags.add(questionTag);
        questionTag.setTag(this);
    }
}
