package com.codestates.stackoverflow.like.service;//package com.codestates.stackoverflow.like.service;

import com.codestates.stackoverflow.like.entity.QuestionLike;
import com.codestates.stackoverflow.like.repository.QuestionLikeRepository;
import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import com.codestates.stackoverflow.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionLikeService {
    private final QuestionService questionService;
    private final QuestionLikeRepository likeRepository;
    private final QuestionRepository questionRepository;

    // like 개수를 늘리고 줄이는 로직
    // like 중복해서 누르면 취소하는 로직

    @Transactional
    public int saveLike(Long questionId, int val) {
        Optional<QuestionLike> findLike = likeRepository.findByQuestion_Id(questionId);
        if (findLike.isEmpty()) {
            Question question = questionRepository.findById(questionId).orElseThrow(RuntimeException::new);
            QuestionLike questionLike = QuestionLike.of(question, val);
            likeRepository.save(questionLike);

            return questionService.modifyLikeCount(questionId, val);
        } else {
            if(findLike.get().getVal() == val) {
                likeRepository.deleteByQuestion_Id(questionId);
                return questionService.modifyLikeCount(questionId, -1);
            } else {
                likeRepository.changeLikeVal(questionId, val);
                return questionService.modifyLikeCount(questionId, -2);
            }
        }
    }

    //like를 늘리고 줄이는 메서드
//    @Transactional
//    public boolean LikeExists(Long questionId) {
//        Optional<QuestionLike> findLike = likeRepository.findByQuestion_Id(questionId);
//        return findLike.isPresent();
//    }
}
