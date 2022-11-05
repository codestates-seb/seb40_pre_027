package com.codestates.stackoverflow.Reply.dto;

import com.codestates.stackoverflow.PageInfo;
import lombok.Getter;

@Getter
public class ReplyAllDto<T> {
    private T data;
    private PageInfo pageInfo;

    public ReplyAllDto(T data, PageInfo pageInfo){
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
