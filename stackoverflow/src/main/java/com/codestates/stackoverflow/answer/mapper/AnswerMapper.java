package com.codestates.stackoverflow.answer.mapper;

import com.codestates.stackoverflow.answer.dto.AnswerDto;
import com.codestates.stackoverflow.answer.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
    Answer AnswerPostDtoToAnswer(AnswerDto.Post answerPost);
    Answer AnswerPatchDtoToAnswer(AnswerDto.Patch answerPatch);
    AnswerDto.Response AnswerToAnswerResponseDto(Answer answers);
    List<AnswerDto.Response> AnswerToAnswerResponseDtos(List<Answer> answers);
}
