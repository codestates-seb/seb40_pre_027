package com.codestates.stackoverflow.tag.mapper;

import com.codestates.stackoverflow.tag.dto.TagDto;
import com.codestates.stackoverflow.tag.entity.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-01T17:39:14+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagDto.Response TagToTagResponse(Tag Tag) {
        if ( Tag == null ) {
            return null;
        }

        TagDto.Response response = new TagDto.Response();

        return response;
    }

    @Override
    public List<TagDto.Response> TagsToTagResponses(List<Tag> tags) {
        if ( tags == null ) {
            return null;
        }

        List<TagDto.Response> list = new ArrayList<TagDto.Response>( tags.size() );
        for ( Tag tag : tags ) {
            list.add( TagToTagResponse( tag ) );
        }

        return list;
    }
}
