package com.codestates.stackoverflow.tag.mapper;

import com.codestates.stackoverflow.tag.dto.TagsResponseDto;
import com.codestates.stackoverflow.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
    TagsResponseDto TagToTagResponseDto(Tag tag);
}
