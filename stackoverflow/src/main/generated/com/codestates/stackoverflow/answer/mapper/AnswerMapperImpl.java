package com.codestates.stackoverflow.answer.mapper;

import com.codestates.stackoverflow.answer.dto.AnswerDto;
import com.codestates.stackoverflow.answer.entity.Answer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-06T17:44:44+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public Answer AnswerPostDtoToAnswer(AnswerDto.Post answerPost) {
        if ( answerPost == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setAnswerContent( answerPost.getAnswerContent() );

        return answer;
    }

    @Override
    public Answer AnswerPatchDtoToAnswer(AnswerDto.Patch answerPatch) {
        if ( answerPatch == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setAnswerId( answerPatch.getAnswerId() );
        answer.setAnswerContent( answerPatch.getAnswerContent() );

        return answer;
    }

    @Override
    public AnswerDto.Response AnswerToAnswerResponseDto(Answer answers) {
        if ( answers == null ) {
            return null;
        }

        AnswerDto.Response.ResponseBuilder response = AnswerDto.Response.builder();

        if ( answers.getAnswerId() != null ) {
            response.answerId( answers.getAnswerId() );
        }
        response.answerContent( answers.getAnswerContent() );
        response.answerLikesCount( answers.getAnswerLikesCount() );
        response.answerCreatedAt( answers.getAnswerCreatedAt() );
        response.answerModifiedAt( answers.getAnswerModifiedAt() );

        return response.build();
    }

    @Override
    public List<AnswerDto.Response> AnswerToAnswerResponseDtos(List<Answer> answers) {
        if ( answers == null ) {
            return null;
        }

        List<AnswerDto.Response> list = new ArrayList<AnswerDto.Response>( answers.size() );
        for ( Answer answer : answers ) {
            list.add( AnswerToAnswerResponseDto( answer ) );
        }

        return list;
    }
}
