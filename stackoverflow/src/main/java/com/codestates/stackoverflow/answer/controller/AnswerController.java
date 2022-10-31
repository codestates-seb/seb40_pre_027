package com.codestates.stackoverflow.answer.controller;

import com.codestates.stackoverflow.PageInfo;
import com.codestates.stackoverflow.answer.dto.AnswerAllDto;
import com.codestates.stackoverflow.answer.dto.AnswerDto;
import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.answer.mapper.AnswerMapper;
import com.codestates.stackoverflow.answer.service.AnswerService;
import com.codestates.stackoverflow.answerLikes.service.AnswerLikesService;
import com.codestates.stackoverflow.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AnswerController {

    private final AnswerMapper mapper;
    private final AnswerService answerService;
    private final AnswerLikesService answerLikesService;

    @PostMapping("/{question-id}")
    public ResponseEntity postAnswer(@PathVariable("question-id") @Positive long questionId,
                                     @RequestBody @Valid AnswerDto.Post answerPost){
        Answer answer = answerService.createAnswer(mapper.AnswerPostDtoToAnswer(answerPost),questionId);
        return new ResponseEntity<>(mapper.AnswerToAnswerResponseDto(answer), HttpStatus.CREATED);
    }

    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") @Positive long answerId,
                                      @RequestBody @Valid AnswerDto.Patch answerPatch){
        Answer answer = mapper.AnswerPatchDtoToAnswer(answerPatch);
        answer.setAnswerId(answerId);
        answerService.updateAnswer(answer);
        return new ResponseEntity<>(mapper.AnswerToAnswerResponseDto(answer),HttpStatus.OK);
    }

    //10.30 question <-> answer mapping add
    @GetMapping("/{question-id}")
    public ResponseEntity getAnswer(@PathVariable("question-id") long questionId,
                                    @Positive @RequestParam int page,
                                    @Positive @RequestParam int size){
        Page<Answer> pageAnswer = answerService.getAnswers(questionId, PageRequest.of(page - 1, size));
        List<Answer> answers = pageAnswer.getContent();

        return new ResponseEntity<>(mapper.AnswerToAnswerResponseDtos(answers), HttpStatus.OK);
    }
    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") Long answerId){
        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/like/{answer-id}")
    public @ResponseBody long like(@PathVariable("answer-id")Long answerId) {
        return answerLikesService.saveLike(answerId, 1);
    }

    @PostMapping("/dislike")
    public @ResponseBody long disLike(Long answerId) {
        return answerLikesService.saveLike(answerId, -1);
    }

    @PatchMapping("/bestAnswer")
    public void selectBest(long questionId,long answerId){
        answerService.bestAnswer(questionId, answerId);
    }
}
