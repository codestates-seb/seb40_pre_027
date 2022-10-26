package com.codestates.stackoverflow.question.mapper;

import com.codestates.stackoverflow.question.dto.QuestionDto;
import com.codestates.stackoverflow.question.entity.Question;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-25T16:10:32+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public Question questionPostToQuestion(QuestionDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Question question = new Question();

        question.setTitle( requestBody.getTitle() );
        question.setContent( requestBody.getContent() );
        question.setViewCount( requestBody.getViewCount() );
        question.setVote( requestBody.getVote() );
        question.setCreatedAt( requestBody.getCreatedAt() );
        question.setModifiedAt( requestBody.getModifiedAt() );

        return question;
    }

    @Override
    public Question questionPatchToQuestion(QuestionDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Question question = new Question();

        question.setQuestionId( requestBody.getQuestionId() );
        question.setTitle( requestBody.getTitle() );
        question.setContent( requestBody.getContent() );
        question.setVote( requestBody.getVote() );

        return question;
    }

    @Override
    public QuestionDto.Response questionToQuestionResponse(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionDto.Response response = new QuestionDto.Response();

        if ( question.getQuestionId() != null ) {
            response.setQuestionId( question.getQuestionId() );
        }
        response.setTitle( question.getTitle() );
        response.setContent( question.getContent() );
        response.setViewCount( question.getViewCount() );
        response.setVote( question.getVote() );
        response.setCreatedAt( question.getCreatedAt() );
        response.setModifiedAt( question.getModifiedAt() );

        return response;
    }

    @Override
    public List<QuestionDto.Response> questionsToQuestionResponse(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<QuestionDto.Response> list = new ArrayList<QuestionDto.Response>( questions.size() );
        for ( Question question : questions ) {
            list.add( questionToQuestionResponse( question ) );
        }

        return list;
    }
}
