package com.codestates.stackoverflow.auth.service;

import com.codestates.stackoverflow.auth.provider.JwtProvider;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class JwtService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public JwtService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    public String reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String base64EncodeSecretKey = jwtProvider.encodeBase64SecretKey(jwtProvider.getSecretKey());
        String accessToken = jwtProvider.getAccessTokenFromRequest(request);
        String rowRefreshToken = jwtProvider.getRefreshTokenFromRequest(request);

        // 전달받은 refresh 검증
        if (!jwtProvider.verifySignature(rowRefreshToken, base64EncodeSecretKey)) {
            log.info("Refresh Token 만료");
            response.setStatus(401); // 수정 필요
            throw new RuntimeException("Invalid Refresh Token");
        }

        Map<String, Object> claims = jwtProvider.getClaimsFromExpiredJwt(accessToken, base64EncodeSecretKey);
        String username = claims.get("username").toString();
        log.info("[JwtService - reissueAccessToken] username : " + username);

        String refreshToken = memberRepository.findByEmail(username).get().getRefreshToken().getToken(); // 회원 없을 경우 로직 처리

        log.info("rowRefreshToken : " + rowRefreshToken);
        log.info("refreshToken : " + refreshToken);

        if (!refreshToken.equals(rowRefreshToken)) {
            log.info("Refresh Token 일치하지 않음.");
            throw new RuntimeException("Invalid Refresh Token");
        }

        String newToken = jwtProvider.generateAccessToken(claims,
                username, jwtProvider.getTokenExpiration(jwtProvider.getAccessTokenExpirationMinutes()),
                base64EncodeSecretKey);
        log.info("new {}",SecurityContextHolder.getContext().toString());
        return newToken;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response){

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findByEmail(email).get();
        member.getRefreshToken().setToken("");
        memberRepository.save(member);
        SecurityContextHolder.clearContext();

    }
}
