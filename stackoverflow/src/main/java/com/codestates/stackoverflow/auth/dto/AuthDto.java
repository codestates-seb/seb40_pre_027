package com.codestates.stackoverflow.auth.dto;

import lombok.*;

public class AuthDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {
        private long memberId;
        private String email;
    }
}
