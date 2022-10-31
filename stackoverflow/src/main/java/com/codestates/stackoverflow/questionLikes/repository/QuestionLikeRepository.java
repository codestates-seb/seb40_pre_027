package com.codestates.stackoverflow.questionLikes.repository;

import com.codestates.stackoverflow.questionLikes.entity.QuestionLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionLikeRepository extends JpaRepository<QuestionLikes, Long> {

    Optional<QuestionLikes> findByQuestionId(Long questionId);

    @Modifying
    @Query("DELETE from QuestionLikes l where l.questionId = :questionId")
    void deleteByQuestion_Id(@Param("questionId") Long questionId);
//    void deleteByQuestion_IdAndMember_Id(Long questionId);

    @Modifying
    @Query("UPDATE QuestionLikes l set l.val = :val where l.questionId = :questionId")
    void changeLikeVal(@Param("questionId") Long questionId, @Param("val") int val);
}
