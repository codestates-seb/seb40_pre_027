package com.codestates.stackoverflow.question.controller;

import com.codestates.stackoverflow.question.mapper.QuestionMapper;
import com.codestates.stackoverflow.questionLikes.service.QuestionLikeService;
import com.codestates.stackoverflow.question.dto.QuestionDto;
import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.service.QuestionService;
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
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Validated
@Slf4j
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionLikeService questionLikeService;
    private final QuestionMapper mapper;

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.Post requestBody) {
        Question question = questionService.createQuestion(mapper.questionPostToQuestion(requestBody));

        return new ResponseEntity<>(
                mapper.questionToQuestionResponse(question),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion (@PathVariable("question-id") @Positive Long questionId,
                                         @Valid @RequestBody QuestionDto.Patch requestBody) {
        requestBody.setQuestionId(questionId);
        Question question = questionService.updateQuestion(mapper.questionPatchToQuestion(requestBody));

        return new ResponseEntity<>(
                mapper.questionToQuestionResponse(question),
                HttpStatus.OK);
    }

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive Long questionId,
                                      @RequestParam int answerPage,
                                      @RequestParam int answerSize) {
        System.out.println("[Get Controller(단건)] 동작");
        Question question = questionService
                .findQuestion(questionId, PageRequest.of(answerPage - 1, answerSize));

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
                mapper.questionsToQuestionResponses(questions),
                HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity getQuestionsActive(@Positive @RequestParam int page,
                                             @Positive @RequestParam int size) {
        Page<Question> pageQuestions = questionService.findQuestionsActive(page - 1, size);
        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(
                mapper.questionsToQuestionResponses(questions),
                HttpStatus.OK);
    }

    /**
     * 요청의 tag 전달 방식에 따라 추후 변경 가능
     */
    @GetMapping("/search-tag")
    public ResponseEntity getQuestionsViaTag(
            @RequestParam String tag,
            @RequestParam int page,
            @RequestParam int size) {
            Page<Question> pageQuestions = questionService.findQuestionsByTag(tag, page - 1, size);
            List<Question> questions = pageQuestions.getContent();
        return new ResponseEntity(mapper.questionsToQuestionResponses(questions),
                HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(
            @PathVariable("question-id") @Positive Long questionId) {
            questionService.deleteQuestion(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/like")
    public @ResponseBody int like(Long questionId) {
        return questionLikeService.saveLike(questionId, 1);
    }

    @PostMapping("/dislike")
    public @ResponseBody int disLike(Long questionId) {
        return questionLikeService.saveLike(questionId, -1);
    }
}
