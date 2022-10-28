package com.codestates.stackoverflow.like.controller;

import com.codestates.stackoverflow.like.service.QuestionLikeService;
import com.codestates.stackoverflow.question.service.QuestionService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class QuestionLikeController {

    private final QuestionLikeService questionLikeService;

    @PostMapping("/up/{question-id}")
    public @ResponseBody int like(@PathVariable("question-id") Long questionId) {
        System.out.println("[Like Controller]: up");
        return questionLikeService.saveLike(questionId, 1);
    }

    @PostMapping("/down/{question-id}")
    public @ResponseBody int disLike(@PathVariable("question-id") Long questionId) {
        System.out.println("[Like Controller]: down");
        return questionLikeService.saveLike(questionId, -1);
    }
}
