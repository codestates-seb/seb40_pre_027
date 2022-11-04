package com.codestates.stackoverflow.auth.provider;

import com.codestates.stackoverflow.auth.RefreshToken;
import com.codestates.stackoverflow.exception.BusinessLogicException;
import com.codestates.stackoverflow.exception.ExceptionCode;
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
import org.springframework.transaction.annotation.Propagation;
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
@Transactional
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

    public JwtProvider(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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
    @Transactional
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

    // request에 담겨 있는 Access Token 가져오기
    public String getAccessTokenFromRequest(HttpServletRequest request) {
        String access = request.getHeader("access");
        if (access.isEmpty()) {
            log.error("Headers에서 access를 찾을 수 없습니다.");
            throw new BusinessLogicException(ExceptionCode.ACCESS_TOKEN_NOT_FOUND);
        }
        String token = access.replace("Bearer ", "");
        return token;
    }

    public String getRefreshTokenFromRequest(HttpServletRequest request) {
        String refreshToken = request.getHeader("refresh");
        if (refreshToken.isEmpty()) {
            log.error("Headers에서 refresh를 찾을 수 없습니다.");
            throw new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND);
        }
        return refreshToken;
    }

    // Access 토큰이 만료되어 갱신때 필요한 유저 정보를 얻기 위한 메서드
    public Jws<Claims> getClaims(String token, String base64EncodedSecretKey) {
        log.info("[getClaims] 토큰의 Claims 생성 시작");
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }
    // 위의 메소드랑 중복 리팩토링 해야 함.
    public Map<String, Object> getClaimsFromExpiredJwt(String token, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
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
