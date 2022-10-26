package com.codestates.stackoverflow.question.service;

import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }


    public Question updateQuestion(Question question) {
        Question findQuestion = findValidQuestion(question.getQuestionId());

        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getContent())
                .ifPresent(findQuestion::setContent);
        Optional.ofNullable(question.getVote())
                .ifPresent(findQuestion::setVote);

        return questionRepository.save(findQuestion);
    }

    public Question findQuestion(Long questionId) {
        return findValidQuestion(questionId);
    }

    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size,
                Sort.by("questionId").descending()));
    }

    public void deleteQuestion(long questionId) {
        Question findQuestion = findValidQuestion(questionId);
        questionRepository.delete(findQuestion);
    }

    @Transactional(readOnly = true)
    public Question findValidQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        return optionalQuestion.orElseThrow(() -> new RuntimeException());
    }
}
