package com.codestates.stackoverflow.auth.filter;

import com.codestates.stackoverflow.auth.dto.LoginDto;
import com.codestates.stackoverflow.auth.provider.JwtProvider;
import com.codestates.stackoverflow.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("[attemptAuthentication] 로그인 요청 시작");
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginRequest = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        log.info("[attemptAuthentication] " + loginRequest.getEmail());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        log.info("[attemptAuthentication] 로그인 요청 종료 (UsernamePasswordAuthenticationToken 생성 완료)");
        return authenticationManager.authenticate(authenticationToken);
    }

    // 로그인 검증이 성공하였을 때 실행되는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        log.info("[successfulAuthentication] 로그인 Request 정보로 JWT 생성 시작");
        Member member = (Member) authResult.getPrincipal();
        // request 정보로 JWT 생성
        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member);

        // 생성된 토큰 response 헤더 쪽에 담아 준다.
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);
        log.info("[successfulAuthentication] Response에 JWT 삽입");

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);

    }


    private String delegateAccessToken(Member member) {
        log.info("[delegateAccessToken] 로그인 Request 정보로 JWT-Access 생성 시작");
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtProvider.getTokenExpiration(jwtProvider.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtProvider.encodeBase64SecretKey(jwtProvider.getSecretKey());

        String accessToken = jwtProvider.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
        log.info("[delegateAccessToken] 로그인 Request 정보로 JWT-Access 생성 완료");
        return accessToken;
    }

    private String delegateRefreshToken(Member member) {
        log.info("[delegateAccessToken] 로그인 Request 정보로 JWT-Refresh 생성 시작");
        String subject = member.getEmail();
        Date expiration = jwtProvider.getTokenExpiration(jwtProvider.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtProvider.encodeBase64SecretKey(jwtProvider.getSecretKey());

        String refreshToken = jwtProvider.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
        log.info("[delegateAccessToken] 로그인 Request 정보로 JWT-Refresh 생성 완료");
        return refreshToken;
    }
}
