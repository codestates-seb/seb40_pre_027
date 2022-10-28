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
    private final AnswerService answerService;
    private final ReplyMapper mapper;

    public ReplyController(ReplyService replyService,
                           ReplyMapper mapper,
                           AnswerService answerService){
        this.replyService = replyService;
        this.mapper = mapper;
        this.answerService = answerService;
    }

    @PostMapping("/{answer-id}")
    public ResponseEntity postReply(@PathVariable("answer-id") long answerId,
                                    @Valid @RequestBody ReplyDto.post post){
        post.setAnswerId(answerId);
        Reply reply = mapper.ReplyPostDtoToReply(post);
        Answer answer = answerService.findAnswer(answerId);
        reply.setReplies(answer);
        long replyWriterId = reply.getReplyWriterId();
        Reply makeReply = replyService.createReply(reply,replyWriterId);
        ReplyDto.response response = mapper.ReplyToReplyResponseDto(makeReply);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity patchReply(@PathVariable("reply-id") Long replyId,
                                     @Valid @RequestBody ReplyDto.patch patch){
        patch.setReplyId(replyId);
        Reply reply = replyService.updateReply(mapper.ReplyPatchDtoToReply(patch));
        return new ResponseEntity<>(mapper.ReplyToReplyResponseDto(reply),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getReply(@Positive @RequestParam int page ,
                                @Positive @RequestParam int size){
        Page<Reply> replyPage = replyService.getReplies(page-1,size);
        PageInfo pageInfo = new PageInfo(page,size,(int)replyPage.getTotalElements(),replyPage.getTotalPages());

        List<Reply> replies = replyPage.getContent();
        List<ReplyDto.response> response = mapper.ReplyToReplyResponseDtos(replies);

        return new ResponseEntity(new ReplyAllDto(response,pageInfo),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteReply(@PathVariable("answer-id") Long answerId,
                                      @PathVariable("reply-id") Long replyId){
        replyService.deleteReply(replyId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
