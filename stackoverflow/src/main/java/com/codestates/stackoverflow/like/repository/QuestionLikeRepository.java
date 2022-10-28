package com.codestates.stackoverflow.like.repository;

import com.codestates.stackoverflow.like.entity.QuestionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionLikeRepository extends JpaRepository<QuestionLike, Long> {
    @Query(value = "SELECT q from Question_Like q where q.questionId = :questionId", nativeQuery = true)
    Optional<QuestionLike> findByQuestion_Id(@Param("questionId") Long questionId);
//    Optional<QuestionLike> findByQuestion_IdAndMember_Id(Long questionId);

    @Modifying
    @Query(value = "DELETE from Question_Like q where q.questionId = :questionId", nativeQuery = true)
    void deleteByQuestion_Id(@Param("questionId") Long questionId);
//    void deleteByQuestion_IdAndMember_Id(Long questionId);

    @Modifying
    @Query(value = "UPDATE q from Question_Like q set q.val = :val where q.questionId = :questionId", nativeQuery = true)
    void changeLikeVal(@Param("questionId") Long questionId, @Param("val") int val);
}
