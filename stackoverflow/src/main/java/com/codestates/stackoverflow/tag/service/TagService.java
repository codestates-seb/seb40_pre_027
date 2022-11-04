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
import org.springframework.scheduling.annotation.Scheduled;
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
    private final QuestionTagRepository questionTagRepository;

    public List<Tag> saveTags(List<Tag> tagList, Long questionId) {
        log.info("[saveTags] 동작");
        for (Tag tag : tagList) {
            Optional<Tag> optionalTag = tagRepository.findByTagName(tag.getTagName());
            if (optionalTag.isEmpty()) {
                tag = tagRepository.save(tag);
            } else {
                tag = optionalTag.get();
            }
            log.info("[saveTags] tag: " + tag);
            QuestionTag questionTag = new QuestionTag(questionId, tag.getTagId(), tag.getTagName());
            questionTagRepository.save(questionTag);
            log.info("[saveTags] questionTag: " + questionTag);
        }
        return tagList;
    }

    /**
     * Popular, Name, New 기준별 정렬된 태그 페이지 조회
     */
    public List<Tag> findTagsPopular(int page) {
        Page<Tag> tagPage = tagRepository.findByOrderByAskedTotal(PageRequest.of(page, 36));
        log.info("[findTagsPopular]: " + tagPage.getContent());
        return tagPage.getContent();
    }

    public List<Tag> findTagsName(int page) {
        Page<Tag> tagPage = tagRepository.findByOrderByTagNameAsc(PageRequest.of(page, 36));
        return tagPage.getContent();
    }

    public List<Tag> findTagsNew(int page) {
        Page<Tag> tagPage = tagRepository.findByOrderByTagIdDesc(PageRequest.of(page, 36));
        return tagPage.getContent();
    }

//    public int findNumberOfQuestionsWithTag (String tagName) {
//        return questionTagRepository.findNumberOfQuestionsWithTag(tagName);
//    }
//
//    public int findNumberOfQuestionsWithTagAskedToday(String tagName) {
//        LocalDateTime since = LocalDate.now().atStartOfDay();
//        return questionTagRepository.findNumberOfQuestionsWithTagSince(tagName, since);
//    }
//
//    public int findNumberOfQuestionsWithTagAskedThisWeek(String tagName) {
//        TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
//        LocalDateTime since = LocalDate.now().atStartOfDay().with(fieldUS, 1);
//        return questionTagRepository.findNumberOfQuestionsWithTagSince(tagName, since);
//    }

    //30분마다 Tag 테이블에서 각 태그가 달린 게시글의 수를 최신화 (매 시각 0분 0초, 30분 0초에 실행)
//    @Scheduled(cron = "0 0,30 * * * *")
//    public void updateTagQuestionsCount() {
//        List<Tag> tags = tagRepository.findAll();
//        tags.forEach(tag -> {
//            String tagName = tag.getTagName();
//            int currentAskedTotal = findNumberOfQuestionsWithTag(tagName);
//            int currentAskedToday = findNumberOfQuestionsWithTagAskedToday(tagName);
//            int currentAskedThisWeek = findNumberOfQuestionsWithTagAskedThisWeek(tagName);
//            if (tag.getAskedTotal() != currentAskedTotal) {
//                tag.setAskedTotal(currentAskedTotal);
//                tagRepository.save(tag);
//            }
//            else if (tag.getQuestionsAskedToday() != currentAskedToday) {
//                tag.setQuestionsAskedToday(currentAskedToday);
//                tagRepository.save(tag);
//            }
//            else if (tag.getQuestionsAskedThisWeek() != currentAskedThisWeek) {
//                tag.setQuestionsAskedThisWeek(currentAskedThisWeek);
//                tagRepository.save(tag);
//            }
//            log.info("[모든 Tag의 QuestionCount Update]: ");
//        });
//    }
}
