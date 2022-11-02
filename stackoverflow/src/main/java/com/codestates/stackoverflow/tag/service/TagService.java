package com.codestates.stackoverflow.tag.service;

import com.codestates.stackoverflow.question.entity.QuestionTag;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import com.codestates.stackoverflow.question.repository.QuestionTagRepository;
import com.codestates.stackoverflow.tag.entity.Tag;
import com.codestates.stackoverflow.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {
    private final TagRepository tagRepository;
    private final QuestionRepository questionRepository;
    private final QuestionTagRepository questionTagRepository;

    public List<Tag> saveTags(List<Tag> tags) {
        for (Tag tag : tags) {
            Optional<Tag> optionalTag = tagRepository.findByTagName(tag.getTagName());
            System.out.println("[sortTags] 작동");
            if (optionalTag.isEmpty()) {
                createTag(tag);
            } else {
                updateTag(optionalTag.get(), tag.getQuestionTags());
            }
        }
        return tags;
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(Tag tag, List<QuestionTag> questionTags) {
        questionTags.stream().forEach(tag::setQuestionTags);

        return tagRepository.save(tag);
    }

    /**
     * Popular, Name, New 기준별 정렬된 태그 페이지 조회
     */
    public List<Tag> findTagsPopular(int page) {
        Page<Tag> tagPage = tagRepository.findByOrderByAskedTotal(PageRequest.of(page, 36));
        return tagPage.getContent();
    }

    public List<Tag> findTagsName(int page) {
        Page<Tag> tagPage = tagRepository.findByOrderByTagNameAsc(PageRequest.of(page, 36));
        return tagPage.getContent();
    }

    public List<Tag> findTagsNew(int page) {
        Page<Tag> tagPage = tagRepository.findByOrderByCreatedAtDesc(PageRequest.of(page, 36));
        return tagPage.getContent();
    }

    public int findNumberOfQuestionsWithTag (String tagName) {
        return questionTagRepository.findNumberOfQuestionsWithTag(tagName);
    }

    public int findNumberOfQuestionsWithTagAskedToday(String tagName) {
        LocalDateTime since = LocalDate.now().atStartOfDay();
        return questionTagRepository.findNumberOfQuestionsWithTagSince(tagName, since);
    }

    public int findNumberOfQuestionsWithTagAskedThisWeek(String tagName) {
        TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
        LocalDateTime since = LocalDate.now().atStartOfDay().with(fieldUS, 1);
//        LocalDateTime since = LocalDate.now().atStartOfDay().with(DayOfWeek.MONDAY);
        return questionTagRepository.findNumberOfQuestionsWithTagSince(tagName, since);
    }

    public List<Tag> tagNameArrayToTagList(String[] tagNames) {
        List<Tag> tags = Arrays.stream(tagNames)
                .map(tagName -> Tag.of(tagName))
                .collect(Collectors.toList());

        return tags;
    }

    public void updateTagQuestionsCount() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Tag> tags = tagRepository.findAll();
                tags.forEach(tag -> {
                    String tagName = tag.getTagName();
                    int currentAskedTotal = findNumberOfQuestionsWithTag(tagName);
                    int currentAskedToday = findNumberOfQuestionsWithTagAskedToday(tagName);
                    int currentAskedThisWeek = findNumberOfQuestionsWithTagAskedThisWeek(tagName);
                    if (tag.getAskedTotal() != currentAskedTotal) {
                        tag.setAskedTotal(currentAskedTotal);
                        tagRepository.save(tag);
                    }
                    else if (tag.getQuestionsAskedToday() != currentAskedToday) {
                        tag.setQuestionsAskedToday(currentAskedToday);
                        tagRepository.save(tag);
                    }
                    else if (tag.getQuestionsAskedThisWeek() != currentAskedThisWeek) {
                        tag.setQuestionsAskedThisWeek(currentAskedThisWeek);
                        tagRepository.save(tag);
                    }
                    log.info("[모든 Tag의 QuestionCount Update]: ");
                });
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 3600, TimeUnit.SECONDS);
    }
}
