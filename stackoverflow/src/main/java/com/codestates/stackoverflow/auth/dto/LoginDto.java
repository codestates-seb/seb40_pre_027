package com.codestates.stackoverflow.auth.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String username;
    private String password;

}
