package com.codestates.stackoverflow.tag.service;

import com.codestates.stackoverflow.question.entity.QuestionTag;
import com.codestates.stackoverflow.question.repository.QuestionTagRepository;
import com.codestates.stackoverflow.tag.entity.Tag;
import com.codestates.stackoverflow.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {
    private final TagRepository tagRepository;
    private final QuestionTagRepository questionTagRepository;

    public List<Tag> saveTags(List<Tag> tagList, Long questionId) {
        log.info("[saveTags] 동작");
        List<QuestionTag> questionTags = questionTagRepository.findByQuestionId(questionId);
        Set<Long> tagIdSet = new HashSet<>();
        for (Tag tag : tagList) {
            Optional<Tag> optionalTag = tagRepository.findByTagName(tag.getTagName());

            // 저장된 태그가 있다면
                // 물어본 횟수를 1 늘려준 뒤 저장한다.
                // 게시글_태그 기록이 없다면 생성한다.
            if(optionalTag.isPresent()) {
                tag = optionalTag.get();
                tag.setAskedTotal(tag.getAskedTotal() + 1);
                tag = tagRepository.save(tag);
                if (questionTagRepository.findByQuestionIdAndContent(questionId, tag.getTagName()).isEmpty()) {
                    questionTagRepository.save(new QuestionTag(questionId, tag.getTagId(), tag.getTagName()));
                }
            }
            //저장된 태그가 없다면, 태그와 게시글_태그를 저장한다.
            else {
                tag.setAskedTotal(1);
                tag = tagRepository.save(tag);
                questionTagRepository.save(new QuestionTag(questionId, tag.getTagId(), tag.getTagName()));
            }
            tagIdSet.add(tag.getTagId());
        }
        deleteUnusedQuestionTags(questionTags, tagIdSet);
        return tagList;
    }
    //현재 question의 questionTag의 목록
    //검증하려는 tag
        //questionTag를 순회하면서
            //
    public void deleteUnusedQuestionTags(List<QuestionTag> questionTags, Set<Long> tagIdSet) {
        // questionTag가
        List<Long> idCheckList = questionTags.stream()
                .map(questionTag -> questionTag.getQuestionTagId())
                .collect(Collectors.toList());

        for (QuestionTag questionTag : questionTags) {
            if (tagIdSet.contains(questionTag.getTagId())) {
                idCheckList.remove(questionTag.getQuestionTagId());
            }
        }

        idCheckList.stream().forEach(questionTagRepository::deleteByQuestionTagId);
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
