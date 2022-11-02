package com.codestates.stackoverflow.question.controller;

//import com.codestates.stackoverflow.question.mapper.QuestionMapper;
import com.codestates.stackoverflow.question.mapper.QuestionMapperImpl;
import com.codestates.stackoverflow.questionLikes.service.QuestionLikeService;
import com.codestates.stackoverflow.question.dto.QuestionDto;
import com.codestates.stackoverflow.question.entity.Question;
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
    private final QuestionMapperImpl mapper;

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.Post requestBody) {
        log.info("[postQuestion] 태그 : " + Arrays.toString(requestBody.getTags()));
        Question question = questionService.createQuestion(mapper.questionPostToQuestion(requestBody));
        question.setTags(requestBody.getTags());
        return new ResponseEntity<>(
                mapper.questionToQuestionResponse(question),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") @Positive Long questionId,
                                         @Valid @RequestBody QuestionDto.Patch requestBody) {
        requestBody.setQuestionId(questionId);
        Question question = questionService.updateQuestion(mapper.questionPatchToQuestion(requestBody));

        return new ResponseEntity<>(
                mapper.questionToQuestionResponse(question),
                HttpStatus.OK);
    }

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive Long questionId) {
        Question question = questionService.findQuestion(questionId);

        return new ResponseEntity<>(
                mapper.questionToQuestionResponse(question),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestionsSorted(@RequestParam(required = false) String tab,
                                       @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer size) {
        if(page == null) page = 1;
        if(size == null) size = 30;

        List<Question> questions = questionService.findQuestions(tab, page - 1, size).getContent();

        return new ResponseEntity(mapper.questionsToQuestionResponses(questions),
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity getQuestionsViaKeyword(@RequestParam(name = "q") String keyword,
                                                 @RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size) {
        if(page == null) page = 1;
        if(size == null) size = 30;

        List<Question> questions = questionService.searchQuestions(keyword, page - 1, size);

        return new ResponseEntity<>(
                mapper.questionsToQuestionResponses(questions),
                HttpStatus.OK
        );
    }

    /**
     * 요청의 tag 전달 방식에 따라 추후 변경 가능
     */
    @GetMapping("/tagged/{tag}")
    public ResponseEntity getQuestionsViaTag(
            @PathVariable(value = "tag") String tagName,
            @RequestParam(required = false) String tab,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if(page == null) page = 1;
        if(size == null) size = 30;

        List<Question> questions = questionService.findTaggedQuestions(tagName, tab, page - 1, size).getContent();

        return new ResponseEntity(mapper.questionsToQuestionResponses(questions),
                HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(
            @PathVariable("question-id") @Positive Long questionId) {
            questionService.deleteQuestion(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{question-id}/vote")
    public @ResponseBody int like(@PathVariable("question-id") Long questionId,
                                  @RequestParam int val) {
        return questionLikeService.saveLike(questionId, val);
    }
}
