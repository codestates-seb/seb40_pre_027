package com.codestates.stackoverflow.questionLikes.service;//package com.codestates.stackoverflow.like.service;

import com.codestates.stackoverflow.exception.BusinessLogicException;
import com.codestates.stackoverflow.exception.ExceptionCode;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.service.impl.MemberServiceImpl;
import com.codestates.stackoverflow.questionLikes.entity.QuestionLikes;
import com.codestates.stackoverflow.questionLikes.repository.QuestionLikeRepository;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import com.codestates.stackoverflow.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionLikeService {
    private final QuestionService questionService;
    private final QuestionLikeRepository likeRepository;
    private final QuestionRepository questionRepository;
    private final MemberServiceImpl memberServiceImpl;

    // like 개수를 늘리고 줄이는 로직
    // like 중복해서 누르면 취소하는 로직

    public int saveLike(Long questionId, int val) {
        Member authMember = memberServiceImpl.findAuthenticatedMember();
        Long memberId = authMember.getMemberId();
        Optional<QuestionLikes> findQuestionLikes = likeRepository.findByQuestionIdAndMemberId(questionId, memberId);
        if (findQuestionLikes.isEmpty()) {
            return createLike(questionId, memberId, val);
        } else {
            return updateLike(findQuestionLikes.get(), questionId, memberId, val);
        }
    }

    public int createLike(Long questionId, Long memberId, int val) throws BusinessLogicException{
        questionRepository.findById(questionId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        QuestionLikes questionLikes = QuestionLikes.of(questionId, memberId, val);
        likeRepository.save(questionLikes);

        return questionService.modifyLikeCount(questionId, val);
    }

    public int updateLike(QuestionLikes questionLikes, Long questionId, Long memberId, int val) throws BusinessLogicException{
        if(questionLikes.getVal() == val) {
            likeRepository.deleteByQuestionIdAndMemberId(questionId, memberId);

            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTED);
        } else {
            likeRepository.changeLikeValue(questionId, val);

            if (val == 1)
                return questionService.modifyLikeCount(questionId, 2);
            else
                return questionService.modifyLikeCount(questionId, -2);
        }
    }

    //like를 늘리고 줄이는 메서드
//    @Transactional
//    public boolean LikeExists(Long questionId) {
//        Optional<QuestionLike> findLike = likeRepository.findByQuestion_Id(questionId);
//        return findLike.isPresent();
//    }
}
