package com.codestates.stackoverflow.Reply.controller;

import com.codestates.stackoverflow.PageInfo;
import com.codestates.stackoverflow.Reply.dto.ReplyAllDto;
import com.codestates.stackoverflow.Reply.dto.ReplyDto;
import com.codestates.stackoverflow.Reply.entity.Reply;
import com.codestates.stackoverflow.Reply.mapper.ReplyMapper;
import com.codestates.stackoverflow.Reply.service.ReplyService;
import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.answer.service.AnswerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/reply")
public class ReplyController {
    private final ReplyService replyService;
    private final ReplyMapper mapper;

    public ReplyController(ReplyService replyService,
                           ReplyMapper mapper){
        this.replyService = replyService;
        this.mapper = mapper;
    }

    @PostMapping("/{answer-id}")
    public ResponseEntity postReply(@PathVariable("answer-id") Long answerId,
                                    @Valid @RequestBody ReplyDto.post post){
        Reply reply = replyService.createReply(mapper.ReplyPostDtoToReply(post),answerId);
        return new ResponseEntity(mapper.ReplyToReplyResponseDto(reply),HttpStatus.CREATED);

    }

    @PatchMapping("/{reply-id}")
    public ResponseEntity patchReply(@PathVariable("reply-id") Long replyId,
                                     @Valid @RequestBody ReplyDto.patch patch){
        patch.setReplyId(replyId);
        Reply reply = replyService.updateReply(mapper.ReplyPatchDtoToReply(patch));
        return new ResponseEntity<>(mapper.ReplyToReplyResponseDto(reply),HttpStatus.OK);
    }

    @GetMapping("/{answer-id}")
    public ResponseEntity getReply(@PathVariable("answer-id") Long answerId,
                                @Positive @RequestParam int page ,
                                @Positive @RequestParam int size){
        Page<Reply> pageReply = replyService.getReplies(answerId, PageRequest.of(page - 1, size));
        List<Reply> replies = pageReply.getContent();
        return new ResponseEntity<>(mapper.ReplyToReplyResponseDtos(replies), HttpStatus.OK);
    }

    @DeleteMapping("/{reply-id}")
    public ResponseEntity deleteReply(@PathVariable("reply-id") Long replyId){
        replyService.deleteReply(replyId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
