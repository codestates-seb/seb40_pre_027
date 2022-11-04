package com.codestates.stackoverflow.tag.entity;

import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.entity.QuestionTag;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column
    private int askedTotal;

    @Column
    private int questionsAskedToday;

    @Column
    private int questionsAskedThisWeek;

//    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
//    @JsonManagedReference
//    private List<QuestionTag> questionTags = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
