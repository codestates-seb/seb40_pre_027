package com.codestates.stackoverflow.auth.provider;

import com.codestates.stackoverflow.auth.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    @Getter
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    // Refresh Token 추후 구현 예정
    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }


    @PostConstruct
    protected void init() {
        log.info("[init] JwtTokenProvider - secretKey 초기화 시작");
        secretKey = Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
        log.info("[init] JwtTokenProvider - secretKey 초기화 완료");
    }

    public String generateAccessToken(Map<String, Object> claims,
                                    String subject,
                                    Date expiration,
                                    String base64EncodedSecretKey) {
        log.info("[createAccessToken] 토큰 생성 시작");

        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        log.info("[createAccessToken] 토큰 생성 완료");

        return token;
    }

    public String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
        log.info("[createRefreshToken] 토큰 생성 시작");
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        log.info("[createRefreshToken] 토큰 생성 완료");

        return token;
    }

    public Jws<Claims> getClaims(String token, String base64EncodedSecretKey) {
        log.info("[getClaims] 토큰의 Claims 불러오기 시작");
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        log.info("[getClaims] 토큰의 Claims 불러오기 완료");
        return claims;
    }

    public void verifySignature(String token, String base64EncodedSecretKey) {
        log.info("[verifySignature] 토큰 검증 시작");
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder().setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        log.info("[verifySignature] 토큰 검증 완료");
    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    public Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }


}
