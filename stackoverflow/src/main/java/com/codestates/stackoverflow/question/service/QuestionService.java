package com.codestates.stackoverflow.question.service;

import com.codestates.stackoverflow.comment.entity.Comment;
import com.codestates.stackoverflow.comment.repository.CommentRepository;
import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.entity.QuestionTag;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import com.codestates.stackoverflow.question.repository.QuestionTagRepository;
import com.codestates.stackoverflow.tag.entity.Tag;
import com.codestates.stackoverflow.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionTagRepository questionTagRepository;
    private final CommentRepository commentRepository;
    private final TagService tagService;

    public Question createQuestion(Question question) {
        //tagContent(String 타입)의 배열을 Tag 객체의 리스트로 변경한다.
        Optional.ofNullable(question.getTags())
                .ifPresent(tagNames -> {
                    List<Tag> tags = tagService.tagNameArrayToTagList(tagNames);
//                    mapQuestionAndTags(question, tags);
                    tags.stream().forEach(tag -> {
                        QuestionTag questionTag = QuestionTag.of(question, tag);
                        question.setQuestionTags(questionTag);
                        tag.setQuestionTags(questionTag);
                    });
                    tagService.sortTags(tags);
                });
        //question과 tag를 저장한다.

        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question) {
        Question findQuestion = findValidQuestion(question.getQuestionId());

        Optional.ofNullable(question.getTitle())
                .ifPresent(findQuestion::setTitle);
        Optional.ofNullable(question.getContent())
                .ifPresent(findQuestion::setContent);
        Optional.ofNullable(question.getTags())
                .ifPresent(tagNames -> {
                    List<Tag> tags = tagService.tagNameArrayToTagList(tagNames);
//                    mapQuestionAndTags(question, tags);
                    tags.stream().forEach(tag -> {
                        QuestionTag questionTag = QuestionTag.of(question, tag);
                        question.setQuestionTags(questionTag);
                        tag.setQuestionTags(questionTag);
                    });
                    tagService.sortTags(tags);
                });
        findQuestion.setModifiedAt(LocalDateTime.now());

        return questionRepository.save(findQuestion);
    }

    public Question findQuestion(Long questionId, Pageable pageable) {
        Question findQuestion = findValidQuestion(questionId);
        findQuestion.setViewCount(findQuestion.getViewCount() + 1);
        questionRepository.save(findQuestion);

        List<Comment> comments = commentRepository.findByQuestion(findQuestion, pageable).getContent();
        findQuestion.setComments(comments);

        return findQuestion;
    }

    @Transactional(readOnly = true)
    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size,
                Sort.by("questionId").descending()));
    }

    /**
     * tag가 null이거나 빈 경우 필요한지 추후 검토 후 수정
     */
    @Transactional(readOnly = true)
    public Page<Question> findQuestionsByTag(String tagName, int page, int size) {
        //tag의 tagName이 동일한 경우 페이지
        return questionRepository.findByTagName(tagName, PageRequest.of(page, size,
                Sort.by("questionId").descending()));
    }

    public void deleteQuestion(long questionId) {
        Question findQuestion = findValidQuestion(questionId);
        questionRepository.delete(findQuestion);
    }

//    public void mapQuestionAndTags(Question question, List<Tag> tags) {
//        tags.stream().forEach(tag -> {
//            QuestionTag questionTag = QuestionTag.of(question, tag);
//            question.setQuestionTags(questionTag);
//            tag.setQuestionTags(questionTag);
//        });
//    }

    public int modifyLikeCount(Long questionId, int val) {
        Question findQuestion = findValidQuestion(questionId);
        int newLikeCount = findQuestion.getLikeCount() + val;
        findQuestion.setLikeCount(newLikeCount);

        return questionRepository.save(findQuestion).getLikeCount();
    }

    @Transactional(readOnly = true)
    public Question findValidQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        return optionalQuestion.orElseThrow(RuntimeException::new);
    }
}
