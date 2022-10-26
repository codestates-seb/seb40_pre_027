package com.codestates.stackoverflow.question.mapper;

import com.codestates.stackoverflow.question.dto.QuestionDto;
import com.codestates.stackoverflow.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionPostToQuestion(QuestionDto.Post requestBody);
    Question questionPatchToQuestion(QuestionDto.Patch requestBody);
    QuestionDto.Response questionToQuestionResponse(Question question);
    List<QuestionDto.Response> questionsToQuestionResponse(List<Question> questions);
}
