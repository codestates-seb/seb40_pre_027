package com.codestates.stackoverflow.Reply.service;

import com.codestates.stackoverflow.Reply.entity.Reply;
import com.codestates.stackoverflow.Reply.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository){
        this.replyRepository = replyRepository;
    }

    public Reply createReply(Reply reply,long ReplyWriterId){
        //User user = userRepository.findByUserId(answerCommentWriterId);
        //reply.setReplyWriter(user);
        reply.setReplyCreatedAt(LocalDateTime.now());
        return replyRepository.save(reply);
    }

    public Reply updateReply(Reply reply){
        Reply findReply = findVerifiedReply(reply.getReplyId());
        Optional.ofNullable(reply.getReplyContent())
                .ifPresent(content -> findReply.setReplyContent(content));
        findReply.setReplyModifiedAt(LocalDateTime.now());
        return replyRepository.save(findReply);
    }

    public Page<Reply> getReplies(int page,int size){
        PageRequest pageRequest = PageRequest.of(page,size);
        return replyRepository.findAllByOrderByReplyIdDesc(pageRequest);
    }

    public void deleteReply(Long replyId){
        Reply findReply = findVerifiedReply(replyId);
        replyRepository.delete(findReply);
    }

    public Reply findVerifiedReply(Long replyId){
        Optional<Reply> optionalReply = replyRepository.findById(replyId);
        Reply findReply = optionalReply.orElseThrow(()->new RuntimeException());
        return findReply;
    }

}
