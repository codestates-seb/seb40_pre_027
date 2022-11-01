package com.codestates.stackoverflow.Reply.service;

import com.codestates.stackoverflow.Reply.entity.Reply;
import com.codestates.stackoverflow.Reply.repository.ReplyRepository;
import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.answer.repository.AnswerRepository;
import com.codestates.stackoverflow.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final AnswerService answerService;
    private final AnswerRepository answerRepository;

    public Reply createReply(Reply reply,long answerId) {
        Answer answer = answerService.findVerifiedAnswer(answerId);
        answer.setReplies(reply);
        answerRepository.save(answer);
        return replyRepository.save(reply);
    }

    public Reply updateReply(Reply reply){
        Reply findReply = findVerifiedReply(reply.getReplyId());
        Optional.ofNullable(reply.getReplyContent())
                .ifPresent(content -> findReply.setReplyContent(content));
        findReply.setReplyModifiedAt(LocalDateTime.now());
        return replyRepository.save(findReply);
    }

    public Page<Reply> getReplies(long answerId , Pageable pageable){
        Answer findAnswer = answerService.findVerifiedAnswer(answerId);
        return replyRepository.findByAnswer(findAnswer,pageable);
        //PageRequest pageRequest = PageRequest.of(page,size);
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
