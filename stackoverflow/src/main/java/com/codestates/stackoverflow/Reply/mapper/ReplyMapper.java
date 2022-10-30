package com.codestates.stackoverflow.Reply.mapper;

import com.codestates.stackoverflow.Reply.dto.ReplyDto;
import com.codestates.stackoverflow.Reply.entity.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReplyMapper {
    Reply ReplyPostDtoToReply(ReplyDto.post post);
    Reply ReplyPatchDtoToReply(ReplyDto.patch patch);
    ReplyDto.response ReplyToReplyResponseDto(Reply reply);
    List<ReplyDto.response> ReplyToReplyResponseDtos(List<Reply> replies);

}
