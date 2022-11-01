package com.codestates.stackoverflow.Reply.mapper;

import com.codestates.stackoverflow.Reply.dto.ReplyDto;
import com.codestates.stackoverflow.Reply.entity.Reply;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-01T15:23:17+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class ReplyMapperImpl implements ReplyMapper {

    @Override
    public Reply ReplyPostDtoToReply(ReplyDto.post post) {
        if ( post == null ) {
            return null;
        }

        Reply reply = new Reply();

        reply.setReplyContent( post.getReplyContent() );

        return reply;
    }

    @Override
    public Reply ReplyPatchDtoToReply(ReplyDto.patch patch) {
        if ( patch == null ) {
            return null;
        }

        Reply reply = new Reply();

        reply.setReplyId( patch.getReplyId() );
        reply.setReplyContent( patch.getReplyContent() );

        return reply;
    }

    @Override
    public ReplyDto.response ReplyToReplyResponseDto(Reply reply) {
        if ( reply == null ) {
            return null;
        }

        ReplyDto.response response = new ReplyDto.response();

        response.setReplyId( reply.getReplyId() );
        response.setReplyContent( reply.getReplyContent() );
        response.setReplyCreatedAt( reply.getReplyCreatedAt() );

        return response;
    }

    @Override
    public List<ReplyDto.response> ReplyToReplyResponseDtos(List<Reply> replies) {
        if ( replies == null ) {
            return null;
        }

        List<ReplyDto.response> list = new ArrayList<ReplyDto.response>( replies.size() );
        for ( Reply reply : replies ) {
            list.add( ReplyToReplyResponseDto( reply ) );
        }

        return list;
    }
}
