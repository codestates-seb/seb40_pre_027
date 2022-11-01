package com.codestates.stackoverflow.member.dto;

import com.codestates.stackoverflow.answer.dto.AnswerDto;
import com.codestates.stackoverflow.answer.entity.Answer;
import com.codestates.stackoverflow.question.dto.QuestionDto;
import com.codestates.stackoverflow.question.entity.Question;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class MemberDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestSignup {
        @NotBlank(message = "이름는 필수 입력 값입니다.")
        private String name;
//        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
//        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseSignup {
        private long memberId;
        private String name;
        private String email;
        private LocalDateTime createdDate;
        private LocalDateTime loginDate;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Profile {
        // 이미지 url 추가 예정
        private String name;
        private LocalDateTime createdDate;
        private LocalDateTime loginDate;
        private String location;
        private String title;
        private String introduction;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Activity {
        private String name;
        private LocalDateTime createdDate;
        private LocalDateTime loginDate;
        private List<QuestionDto.Response> questions;
        private List<AnswerDto.Response> answers;

    }

}