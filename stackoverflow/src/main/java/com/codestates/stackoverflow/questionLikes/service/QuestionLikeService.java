package com.codestates.stackoverflow.questionLikes.service;//package com.codestates.stackoverflow.like.service;

import com.codestates.stackoverflow.question.entity.Question;
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

    // like 개수를 늘리고 줄이는 로직
    // like 중복해서 누르면 취소하는 로직

    public int saveLike(Long questionId, int val) {
        Optional<QuestionLikes> findQuestionLikes = likeRepository.findByQuestionId(questionId);
        if (findQuestionLikes.isEmpty()) {
            return createLike(questionId, val);
        } else {
            return updateLike(findQuestionLikes.get(), questionId, val);
        }
    }

    public int createLike(Long questionId, int val) {
        questionRepository.findById(questionId).orElseThrow(RuntimeException::new);

        QuestionLikes questionLikes = QuestionLikes.of(questionId, val);
        likeRepository.save(questionLikes);

        return questionService.modifyLikeCount(questionId, val);
    }

    public int updateLike(QuestionLikes questionLikes, Long questionId, int val) {
        if(questionLikes.getVal() == val) {
            likeRepository.deleteByQuestion_Id(questionId);

            return questionService.modifyLikeCount(questionId, -1);
        } else {
            likeRepository.changeLikeVal(questionId, val);

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
