//package com.codestates.stackoverflow.question.mapper;
//
//import com.codestates.stackoverflow.question.dto.QuestionDto;
//import com.codestates.stackoverflow.question.entity.Question;
//import com.codestates.stackoverflow.tag.entity.Tag;
//import org.mapstruct.Mapper;
//import org.mapstruct.ReportingPolicy;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface QuestionMapper {
//
//    Question questionPostToQuestion(QuestionDto.Post requestBody);
//    Question questionPatchToQuestion(QuestionDto.Patch requestBody);
//    QuestionDto.Response questionToQuestionResponse(Question question);
//    List<QuestionDto.Response> questionsToQuestionResponses(List<Question> questions);
//}
