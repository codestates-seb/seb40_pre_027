package com.codestates.stackoverflow.answer.controller;

import com.codestates.stackoverflow.PageInfo;
import com.codestates.stackoverflow.answer.dto.AnswerAllDto;
import com.codestates.stackoverflow.answer.dto.AnswerDto;
import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.answer.mapper.AnswerMapper;
import com.codestates.stackoverflow.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    @PostMapping("/{question-id}")
    public ResponseEntity postAnswer(@PathVariable("question-id") @Positive long questionId,
                                     @RequestBody @Valid AnswerDto.Post answerPost){
        answerPost.setQuestionId(questionId);
        Answer answer = answerService.createAnswer(mapper.AnswerPostDtoToAnswer(answerPost));
        //Answer answer = mapper.AnswerPostDtoToAnswer(answerPost);
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

    //페이지네이션 구현
    @GetMapping
    public ResponseEntity getAnswer(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size){
        Page<Answer> answerPage = answerService.getAnswers(page-1,size);
        PageInfo pageInfo = new PageInfo(page,size,(int)answerPage.getTotalElements(),answerPage.getTotalPages());

        List<Answer> answers = answerPage.getContent();
        List<AnswerDto.Response> response = mapper.AnswerToAnswerResponseDtos(answers);

        return new ResponseEntity(new AnswerAllDto(response,pageInfo),HttpStatus.OK);
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") Long answerId){
        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
