package com.codestates.stackoverflow.tag.controller;

import com.codestates.stackoverflow.tag.entity.Tag;
import com.codestates.stackoverflow.tag.mapper.TagMapper;
import com.codestates.stackoverflow.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper mapper;

    @GetMapping
    public ResponseEntity getTags(@RequestParam(required = false) Integer page,
                                  @RequestParam(required = false) String tab) {
        if (page == null) page = 1;
        if (tab == null) tab = "popular";
        tab = tab.toLowerCase();
        List<Tag> tags = new ArrayList<>();

        if (tab.equals("popular")) {
            tags = tagService.findTagsPopular(page - 1);
        }
        else if (tab.equals("name")) {
            tags = tagService.findTagsName(page - 1);
        }
        else if (tab.equals("new")) {
            tags = tagService.findTagsNew(page - 1);
        }

        return new ResponseEntity(mapper.TagsToTagResponses(tags), HttpStatus.OK);
    };
}
