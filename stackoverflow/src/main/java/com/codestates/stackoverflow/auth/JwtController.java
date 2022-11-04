package com.codestates.stackoverflow.auth;

import com.codestates.stackoverflow.auth.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/user")
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // 만료된 토큰 재발급
    @GetMapping("/auth/reissue")
    public ResponseEntity reissue(HttpServletRequest request, HttpServletResponse response) {
        log.info("reissue 접근 시작");
        String newToken = jwtService.reissueAccessToken(request, response);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("access", "Bearer " + newToken);
        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).build();
    }

    // 로그아웃 기능 구현
    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
       jwtService.logout(request, response);

       return ResponseEntity.ok("로그아웃이 정상적으로 이루어졌습니다.");
    }
}
