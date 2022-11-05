package com.codestates.stackoverflow.comment.mapper;

import com.codestates.stackoverflow.comment.dto.CommentDto;
import com.codestates.stackoverflow.comment.entity.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-05T13:28:00+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostToComment(CommentDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setContent( requestBody.getContent() );

        return comment;
    }

    @Override
    public Comment commentPatchToComment(CommentDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId( requestBody.getCommentId() );
        comment.setContent( requestBody.getContent() );

        return comment;
    }

    @Override
    public CommentDto.Response commentToCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto.Response response = new CommentDto.Response();

        response.setCommentId( comment.getCommentId() );
        response.setContent( comment.getContent() );
        response.setCreatedAt( comment.getCreatedAt() );

        return response;
    }

    @Override
    public List<CommentDto.Response> commentsToCommentResponses(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentDto.Response> list = new ArrayList<CommentDto.Response>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( commentToCommentResponse( comment ) );
        }

        return list;
    }
}