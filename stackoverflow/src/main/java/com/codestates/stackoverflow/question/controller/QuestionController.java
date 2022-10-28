package com.codestates.stackoverflow.question.controller;

import com.codestates.stackoverflow.question.dto.QuestionDto;
import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.mapper.QuestionMapper;
import com.codestates.stackoverflow.question.service.QuestionService;
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
@RequestMapping
@RequiredArgsConstructor
@Validated
@Slf4j
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper mapper;

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.Post requestBody) {
        Question question = questionService.createQuestion(mapper.questionPostToQuestion(requestBody));

        return new ResponseEntity<>(
                mapper.questionToQuestionResponse(question),
                HttpStatus.CREATED);
    }

    @PatchMapping("{question-id}")
    public ResponseEntity patchQuestion (@PathVariable("question-id") @Positive Long questionId,
                                         @Valid @RequestBody QuestionDto.Patch requestBody) {
        requestBody.setQuestionId(questionId);
        Question question = questionService.updateQuestion(mapper.questionPatchToQuestion(requestBody));

        return new ResponseEntity<>(
                mapper.questionToQuestionResponse(question),
                HttpStatus.OK);
    }

    @GetMapping("{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive Long questionId) {
        Question question = questionService.findQuestion(questionId);

//        questionService.findLike(questionId);
        return new ResponseEntity<>(
                mapper.questionToQuestionResponse(question),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, size);
        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(
                mapper.questionsToQuestionResponse(questions),
                HttpStatus.OK);
    }

    @DeleteMapping("{question-id}")
    public ResponseEntity deleteQuestion(
            @PathVariable("question-id") @Positive Long questionId) {
            questionService.deleteQuestion(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PostMapping("/like")
//    public @ResponseBody int like(Long questionId) {
//        return questionService.saveLike(questionId);
//    }
//
//    @PostMapping("/dislike")
//    public @ResponseBody int disLike(Long questionId) {
//        return questionService.saveLike(questionId);
//    }
}
