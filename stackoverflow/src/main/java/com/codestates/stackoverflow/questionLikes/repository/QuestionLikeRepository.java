package com.codestates.stackoverflow.questionLikes.repository;

import com.codestates.stackoverflow.questionLikes.entity.QuestionLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionLikeRepository extends JpaRepository<QuestionLikes, Long> {

    Optional<QuestionLikes> findByQuestionIdAndMemberId(Long questionId, Long memberId);

    void deleteByQuestionIdAndMemberId(Long questionId, Long memberId);
}
