package com.codestates.stackoverflow.answerLikes.entity;

import com.codestates.stackoverflow.answer.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerLikesId;


    //answerLikesCount 에 접근하기 위해 OneToOne 으로 매핑해줍니다.
    @OneToOne
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;

    @Column(nullable = false)
    private Long whoAnswerLikeId;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    @ColumnDefault("3")
    private LikesStatus LikeStatus;

    public enum LikesStatus{
        LIKE_CLICK("좋아요"),
        HATE_CLICK("싫어요"),
        NO_CLICK("안눌렀어요");

        @Getter
        private String status;

        LikesStatus(String status){
            this.status = status;
        }

    }
}
