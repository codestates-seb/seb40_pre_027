package com.codestates.stackoverflow.tag.mapper;

import com.codestates.stackoverflow.tag.dto.TagsResponseDto;
import com.codestates.stackoverflow.tag.entity.Tag;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-31T18:11:19+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagsResponseDto TagToTagResponseDto(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagsResponseDto tagsResponseDto = new TagsResponseDto();

        return tagsResponseDto;
    }
}
