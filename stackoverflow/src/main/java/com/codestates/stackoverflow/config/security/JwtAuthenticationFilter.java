package com.codestates.stackoverflow.config.security;

import com.codestates.stackoverflow.member.dto.MemberDto;
import com.codestates.stackoverflow.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {  // (1)
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    // (2)
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    // (3)
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        ObjectMapper objectMapper = new ObjectMapper();    // (3-1)
        MemberDto.LoginPost loginRequest = objectMapper.readValue(request.getInputStream(), MemberDto.LoginPost.class); // (3-2)

        // (3-3)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        return authenticationManager.authenticate(authenticationToken);  // (3-4)
    }

    // (4)
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        Member member = (Member) authResult.getPrincipal();  // (4-1)

        String accessToken = delegateAccessToken(member);   // (4-2)
        String refreshToken = delegateRefreshToken(member); // (4-3)

        response.setHeader("Authorization", "Bearer " + accessToken);  // (4-4)
        response.setHeader("Refresh", refreshToken);                   // (4-5)
    }

    // (5)
    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtProvider.getTokenExpiration(jwtProvider.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtProvider.encodeBase64SecretKey(jwtProvider.getSecretKey());

        String accessToken = jwtProvider.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    // (6)
    private String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expiration = jwtProvider.getTokenExpiration(jwtProvider.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtProvider.encodeBase64SecretKey(jwtProvider.getSecretKey());

        String refreshToken = jwtProvider.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
