package com.codestates.stackoverflow.auth.handler;

import com.codestates.stackoverflow.auth.dto.AuthDto;
import com.codestates.stackoverflow.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* 로그인 검증이 성공적으로 완료되면 최종적으로 호출되는 클래스 */
@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("[onAuthenticationSuccess] 인증이 정상적으로 완료되었습니다.");
        Member principal = (Member) authentication.getPrincipal();

        AuthDto.LoginResponse responseBody = new AuthDto.LoginResponse();
        responseBody.setMemberId(principal.getMemberId());
        responseBody.setEmail(principal.getEmail());

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        String result = objectMapper.writeValueAsString(responseBody);

        log.info(response.getHeader("access"));
        log.info(response.getHeader("refresh"));
        response.getWriter().write(result);

    }
}
