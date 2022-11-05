package com.codestates.stackoverflow.answerLikes.service;

import com.codestates.stackoverflow.answer.repository.AnswerRepository;
import com.codestates.stackoverflow.answer.service.AnswerService;
import com.codestates.stackoverflow.answerLikes.entity.AnswerLikes;
import com.codestates.stackoverflow.answerLikes.repository.AnswerLikesRepository;
import com.codestates.stackoverflow.exception.BusinessLogicException;
import com.codestates.stackoverflow.exception.ExceptionCode;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AnswerLikesService {
    private final AnswerLikesRepository answerLikesRepository;
    private final AnswerRepository answerRepository;
    private final AnswerService answerService;
    private final MemberServiceImpl memberServiceImpl;

    public int saveLike(Long answerId, int val) {
        Member authMember = memberServiceImpl.findAuthenticatedMember();
        Long memberId = authMember.getMemberId();
        Optional<AnswerLikes> findAnswerLikes = answerLikesRepository.findByAnswerIdAndMemberId(answerId, memberId);
        if (findAnswerLikes.isEmpty()) {
            return createLike(answerId, memberId, val);
        } else {
            return updateLike(findAnswerLikes.get(), answerId, val);
        }
    }

    public int createLike(Long questionId, Long memberId, int val) throws BusinessLogicException {
        answerRepository.findById(questionId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        AnswerLikes answerLikes = AnswerLikes.of(questionId, memberId, val);
        answerLikesRepository.save(answerLikes);

        return answerService.modifyLikeCount(questionId, val);
    }

    public int updateLike(AnswerLikes answerLikes, Long answerId, int val) throws BusinessLogicException{
        int originalVal = answerLikes.getVal();
        if(originalVal == 0) {
            answerLikes.setVal(val);
            answerLikesRepository.save(answerLikes);

            return answerService.modifyLikeCount(answerId, val);

        } else if (originalVal == val) {

            throw new BusinessLogicException(ExceptionCode.ALREADY_VOTED);

        } else {

            answerLikes.setVal(0);
            answerLikesRepository.save(answerLikes);

            return answerService.modifyLikeCount(answerId, val);
        }
    }
}
