package com.codestates.stackoverflow.answerLikes.controller;

import com.codestates.stackoverflow.answerLikes.dto.AnswerLikesDto;
import com.codestates.stackoverflow.answerLikes.service.AnswerLikesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping
public class AnswerLikesController {
    private final AnswerLikesService answerLikesService;

    public AnswerLikesController(AnswerLikesService answerLikesService){
        this.answerLikesService = answerLikesService;
    }

    @PatchMapping("/like/{answer-Id}/{whoAnswerLikeId}")
    public ResponseEntity likesAnswer(@RequestBody @PathVariable Long whoAnswerLikeId,
                                      @NotNull @Positive @PathVariable("answer-Id")Long answerId){
        answerLikesService.addLike(whoAnswerLikeId,answerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/hate/{answer-Id}/{whoAnswerLikeId}")
    public ResponseEntity hateAnswer(@RequestBody @PathVariable Long whoAnswerLikeId,
                                     @NotNull @Positive @PathVariable("answer-Id")Long answerId){
        answerLikesService.addHate(whoAnswerLikeId, answerId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
