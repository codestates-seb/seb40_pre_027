package com.codestates.stackoverflow.tag.service;

import com.codestates.stackoverflow.question.entity.QuestionTag;
import com.codestates.stackoverflow.question.repository.QuestionRepository;
import com.codestates.stackoverflow.tag.entity.Tag;
import com.codestates.stackoverflow.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final QuestionRepository questionRepository;

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

    public List<Tag> tagNameArrayToTagList(String[] tagNames) {
        List<Tag> tags = Arrays.stream(tagNames)
                .map(tagName -> Tag.of(tagName))
                .collect(Collectors.toList());

        return tags;
    }
}
