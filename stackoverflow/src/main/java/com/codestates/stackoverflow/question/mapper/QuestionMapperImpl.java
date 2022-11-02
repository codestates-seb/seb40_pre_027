package com.codestates.stackoverflow.question.mapper;

import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.comment.entity.Comment;
import com.codestates.stackoverflow.question.dto.QuestionDto;
import com.codestates.stackoverflow.question.entity.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapperImpl {

    public Question questionPostToQuestion(QuestionDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Question question = new Question();

        question.setTitle( requestBody.getTitle() );
        question.setContent( requestBody.getContent() );
        String[] tags = requestBody.getTags();
        if ( tags != null ) {
            question.setTags( Arrays.copyOf( tags, tags.length ) );
        }

        return question;
    }

    public Question questionPatchToQuestion(QuestionDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Question question = new Question();

        question.setQuestionId( requestBody.getQuestionId() );
        question.setTitle( requestBody.getTitle() );
        question.setContent( requestBody.getContent() );
        String[] tags = requestBody.getTags();
        if ( tags != null ) {
            question.setTags( Arrays.copyOf( tags, tags.length ) );
        }

        return question;
    }

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
        response.setLikeCount( question.getLikeCount() );
        response.setCreatedAt( question.getCreatedAt() );
        response.setModifiedAt( question.getModifiedAt() );
        String[] tags = question.getTags();
        if ( tags != null ) {
            response.setTags( Arrays.copyOf( tags, tags.length ) );
        }
        List<Comment> list = question.getComments();
        if ( list != null ) {
            response.setComments( new ArrayList<Comment>( list ) );
        }
        List<Answer> list1 = question.getAnswers();
        if ( list1 != null ) {
            response.setAnswers( new ArrayList<Answer>( list1 ) );
        }

        return response;
    }

    public List<QuestionDto.Response> questionsToQuestionResponses(List<Question> questions) {
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