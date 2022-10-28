package com.codestates.stackoverflow.like.repository;

import com.codestates.stackoverflow.like.entity.QuestionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionLikeRepository extends JpaRepository<QuestionLike, Long> {
    @Query(value = "SELECT l from QuestionLike l where l.questionId = :questionId")
    Optional<QuestionLike> findByQuestion_Id(@Param("questionId") Long questionId);
//    Optional<QuestionLike> findByQuestion_IdAndMember_Id(Long questionId);

    @Modifying
    @Query("DELETE from QuestionLike l where l.questionId = :questionId")
    void deleteByQuestion_Id(@Param("questionId") Long questionId);
//    void deleteByQuestion_IdAndMember_Id(Long questionId);

    @Modifying
    @Query("UPDATE QuestionLike l set l.val = :val where l.questionId = :questionId")
    void changeLikeVal(@Param("questionId") Long questionId, @Param("val") int val);
}
