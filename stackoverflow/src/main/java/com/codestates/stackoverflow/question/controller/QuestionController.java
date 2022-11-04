package com.codestates.stackoverflow.question.controller;

import com.codestates.stackoverflow.question.dto.MultiResponseDto;
import com.codestates.stackoverflow.question.mapper.QuestionMapper;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import com.codestates.stackoverflow.questionLikes.service.QuestionLikeService;
import com.codestates.stackoverflow.question.dto.QuestionDto;
import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.service.QuestionService;
import com.codestates.stackoverflow.tag.entity.Tag;
import com.codestates.stackoverflow.tag.mapper.TagMapper;
import com.codestates.stackoverflow.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Validated
@Slf4j
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionLikeService questionLikesService;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final TagMapper tagMapper;
    private final TagService tagService;

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.Post requestBody) {
        Question question = questionService.createQuestion(questionMapper.questionPostToQuestion(requestBody));
        List<Tag> tags = tagMapper.tagNamesToTags(requestBody.getTags());
        tags = tagService.saveTags(tags, question.getQuestionId());
        QuestionDto.Response response = questionMapper.questionToQuestionResponse(question);
        response.setTags(requestBody.getTags());

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") @Positive Long questionId,
                                         @Valid @RequestBody QuestionDto.Patch requestBody) {
        requestBody.setQuestionId(questionId);
        Question question = questionService.updateQuestion(questionMapper.questionPatchToQuestion(requestBody));
        List<Tag> tags = tagMapper.tagNamesToTags(requestBody.getTags());
        tagService.saveTags(tags, question.getQuestionId());
        QuestionDto.Response response = questionMapper.questionToQuestionResponse(question);
        response.setTags(requestBody.getTags());

        return new ResponseEntity<>(
                questionMapper.questionToQuestionResponse(question),
                HttpStatus.OK);
    }

    // 질문 1개 겟
    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive Long questionId) {
        Question question = questionService.findQuestion(questionId);

        return new ResponseEntity<>(
                questionMapper.questionToQuestionResponse(question),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestionsSorted(@RequestParam(required = false) String tab,
                                       @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer size) {
        if(tab == null) tab = "Newest";
        if(page == null) page = 1;
        if(size == null) size = 30;
        tab = tab.toLowerCase();
        int count = questionRepository.countAllQuestions();
        System.out.println("총 질문 개수: " + count);
        List<Question> questions = questionService.findQuestions(tab, page - 1, size).getContent();
        List<QuestionDto.Response> responses = questionMapper.questionsToQuestionResponses(questions);

        return new ResponseEntity(new MultiResponseDto(responses, questionRepository.countAllQuestions()),
                HttpStatus.OK);
    }

    /**
     * 질문에 대한 검색 결과를 조회합니다.
     */
    @GetMapping("/search")
    public ResponseEntity getQuestionsViaKeyword(@RequestParam(name = "q") String keyword,
                                                 @RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size) {
        if(page == null) page = 1;
        if(size == null) size = 30;

        List<Question> questions = questionService.searchQuestions(keyword, page - 1, size);
        List<QuestionDto.Response> responses =  questionMapper.questionsToQuestionResponses(questions);

        return new ResponseEntity<>(new MultiResponseDto(responses, questionRepository.countAllQuestions()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(
            @PathVariable("question-id") @Positive Long questionId) {
            questionService.deleteQuestion(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{question-id}/vote")
    public @ResponseBody int like(@PathVariable("question-id") @Positive Long questionId,
                                  @RequestParam int val) {
        return questionLikesService.saveLike(questionId, val);
    }
}
