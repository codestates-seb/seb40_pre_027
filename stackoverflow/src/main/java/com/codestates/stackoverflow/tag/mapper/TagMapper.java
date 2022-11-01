package com.codestates.stackoverflow.tag.mapper;

import com.codestates.stackoverflow.tag.dto.TagDto;
import com.codestates.stackoverflow.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    TagDto.Response TagToTagResponse(Tag Tag);
    List<TagDto.Response> TagsToTagResponses(List<Tag> tags);
}
