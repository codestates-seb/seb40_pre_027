package com.codestates.stackoverflow.tag.mapper;

import com.codestates.stackoverflow.question.entity.Question;
import com.codestates.stackoverflow.question.entity.QuestionTag;
import com.codestates.stackoverflow.tag.dto.TagDto;
import com.codestates.stackoverflow.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    TagDto.Response TagToTagResponse(Tag Tag);
    List<TagDto.Response> TagsToTagResponses(List<Tag> tags);
    default List<Tag> tagNamesToTags(String[] tagNames) {
        List<Tag> tags = Arrays.stream(tagNames)
                .map(tagName -> new Tag(tagName))
                .collect(Collectors.toList());
        return tags;
    }
}
