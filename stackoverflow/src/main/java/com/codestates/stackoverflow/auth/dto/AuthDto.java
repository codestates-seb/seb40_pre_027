package com.codestates.stackoverflow.auth.dto;

import lombok.*;


public class AuthDto {

    @Getter
    @AllArgsConstructor
    public static class LoginDto {
        private String username;
        private String password;

    }
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class tokenDto {
        private String grantType;
        private String accessToken;
        private String refresh;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TokenRequestDto {
        String accessToken;
        String refreshToken;

        @Builder
        public TokenRequestDto(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
