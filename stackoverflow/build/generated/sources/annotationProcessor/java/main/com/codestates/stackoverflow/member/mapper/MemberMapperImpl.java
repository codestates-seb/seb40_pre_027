package com.codestates.stackoverflow.member.mapper;

import com.codestates.stackoverflow.Reply.dto.ReplyDto;
import com.codestates.stackoverflow.Reply.entity.Reply;
import com.codestates.stackoverflow.answer.dto.AnswerDto;
import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.comment.entity.Comment;
import com.codestates.stackoverflow.member.dto.MemberDto;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.question.dto.QuestionDto;
import com.codestates.stackoverflow.question.entity.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-04T14:54:50+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member requestSignupToMember(MemberDto.RequestSignup request) {
        if ( request == null ) {
            return null;
        }

        Member member = new Member();

        member.setName( request.getName() );
        member.setEmail( request.getEmail() );
        member.setPassword( request.getPassword() );

        return member;
    }

    @Override
    public MemberDto.ResponseSignup memberToResponseSignup(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.ResponseSignup responseSignup = new MemberDto.ResponseSignup();

        if ( member.getMemberId() != null ) {
            responseSignup.setMemberId( member.getMemberId() );
        }
        responseSignup.setName( member.getName() );
        responseSignup.setEmail( member.getEmail() );
        responseSignup.setCreatedDate( member.getCreatedDate() );
        responseSignup.setLoginDate( member.getLoginDate() );

        return responseSignup;
    }

    @Override
    public Member profileToMember(MemberDto.Profile request) {
        if ( request == null ) {
            return null;
        }

        Member member = new Member();

        member.setCreatedDate( request.getCreatedDate() );
        member.setLoginDate( request.getLoginDate() );
        member.setName( request.getName() );
        member.setLocation( request.getLocation() );
        member.setTitle( request.getTitle() );
        member.setIntroduction( request.getIntroduction() );

        return member;
    }

    @Override
    public MemberDto.Profile memberToProfile(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Profile profile = new MemberDto.Profile();

        profile.setName( member.getName() );
        profile.setLoginDate( member.getLoginDate() );
        profile.setCreatedDate( member.getCreatedDate() );
        profile.setLocation( member.getLocation() );
        profile.setTitle( member.getTitle() );
        profile.setIntroduction( member.getIntroduction() );

        return profile;
    }

    @Override
    public MemberDto.Activity memberToActivity(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Activity activity = new MemberDto.Activity();

        activity.setName( member.getName() );
        activity.setLoginDate( member.getLoginDate() );
        activity.setCreatedDate( member.getCreatedDate() );
        activity.setQuestions( questionListToResponseList( member.getQuestions() ) );
        activity.setAnswers( answerListToResponseList( member.getAnswers() ) );
        activity.setReplies( replyListToresponseList( member.getReplies() ) );

        return activity;
    }

    protected QuestionDto.Response questionToResponse(Question question) {
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

    protected List<QuestionDto.Response> questionListToResponseList(List<Question> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionDto.Response> list1 = new ArrayList<QuestionDto.Response>( list.size() );
        for ( Question question : list ) {
            list1.add( questionToResponse( question ) );
        }

        return list1;
    }

    protected AnswerDto.Response answerToResponse(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerDto.Response.ResponseBuilder response = AnswerDto.Response.builder();

        if ( answer.getAnswerId() != null ) {
            response.answerId( answer.getAnswerId() );
        }
        response.answerContent( answer.getAnswerContent() );
        response.answerLikesCount( answer.getAnswerLikesCount() );
        response.answerCreatedAt( answer.getAnswerCreatedAt() );
        response.answerModifiedAt( answer.getAnswerModifiedAt() );

        return response.build();
    }

    protected List<AnswerDto.Response> answerListToResponseList(List<Answer> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerDto.Response> list1 = new ArrayList<AnswerDto.Response>( list.size() );
        for ( Answer answer : list ) {
            list1.add( answerToResponse( answer ) );
        }

        return list1;
    }

    protected ReplyDto.response replyToresponse(Reply reply) {
        if ( reply == null ) {
            return null;
        }

        ReplyDto.response response = new ReplyDto.response();

        response.setReplyId( reply.getReplyId() );
        response.setReplyContent( reply.getReplyContent() );
        response.setReplyCreatedAt( reply.getReplyCreatedAt() );

        return response;
    }

    protected List<ReplyDto.response> replyListToresponseList(List<Reply> list) {
        if ( list == null ) {
            return null;
        }

        List<ReplyDto.response> list1 = new ArrayList<ReplyDto.response>( list.size() );
        for ( Reply reply : list ) {
            list1.add( replyToresponse( reply ) );
        }

        return list1;
    }
}
