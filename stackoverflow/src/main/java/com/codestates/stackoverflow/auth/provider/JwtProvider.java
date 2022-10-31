package com.codestates.stackoverflow.auth.provider;

import com.codestates.stackoverflow.auth.RefreshToken;
import com.codestates.stackoverflow.exception.BusinessLogicException;
import com.codestates.stackoverflow.member.entity.Member;
import com.codestates.stackoverflow.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    @Getter
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Getter
    @Setter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Setter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    // refresh Token 저장용
    private final MemberRepository memberRepository;

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @PostConstruct
    protected void init() {
        log.info("[init] JwtTokenProvider - secretKey 초기화 시작");
        secretKey = Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
        log.info("[init] JwtTokenProvider - secretKey 초기화 완료");
    }

    // Access Token 생성 메서드
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

    // Refresh Token 생성 메서드
    @Transactional
    public String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
        log.info("[createRefreshToken] 토큰 생성 시작");
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        log.info("generateRefreshToken " + subject.toString());
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        Member member = memberRepository.findByEmail(subject).get();
        log.info("member : " + member.toString());
        member.getRefreshToken().setToken(token);
        memberRepository.save(member);

        log.info("[createRefreshToken] 토큰 생성 완료");

        return token;
    }

    // Access Token을 검사하고 얻은 정보로 Authentication 객체 생성
//    public String reissueAccessToken(Map<String, Object> claims , String refreshToken, String base64EncodedSecretKey) {
//        String username = claims.get("username").toString();
//        Member member = memberRepository.findByEmail(username).get();
//
//        String verifiedRefreshToken = member.getRefreshToken();
//
//        if (!(refreshToken == verifiedRefreshToken)) {
//            throw new RuntimeException("토큰 이상함");
//        }
//
//    }

    // request에 담겨 있는 토큰 가져오기
    public String getAccessTokenFromRequest(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        return jws;
    }

    public String getRefreshTokenFromRequest(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh");
        return refreshToken;
    }

    // Access 토큰이 만료되어 갱신때 필요한 유저 정보를 얻기 위한 메서드
    public Jws<Claims> getClaims(String token, String base64EncodedSecretKey) {
        log.info("[getClaims] 토큰의 Claims 생성 시작");
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        log.info("[getClaims] 토큰의 Claims 생성 시작");
        return claims;
    }

    // 토큰의 유효 및 만료 시간 확인 메서드
    public boolean verifySignature(String token, String base64EncodedSecretKey) {
        log.info("[verifySignature] 토큰 검증 시작");
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            log.info("[verifySignature] claims : " + claims.getBody().toString());

            return !claims.getBody().getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT Signature");
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token");
            return false;
        } catch (IllegalStateException e) {
            log.error("JWT token is invalid");
            return false;
        }
    }

    // 토큰

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
