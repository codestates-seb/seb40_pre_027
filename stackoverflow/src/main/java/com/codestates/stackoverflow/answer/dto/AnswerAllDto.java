package com.codestates.stackoverflow.answer.dto;
import com.codestates.stackoverflow.PageInfo;
import lombok.Getter;

@Getter
public class AnswerAllDto<T> {
    private T data;
    private PageInfo pageInfo;

    public AnswerAllDto(T data, PageInfo pageInfo){
        this.data = data;
        this.pageInfo = pageInfo;
    }

}
