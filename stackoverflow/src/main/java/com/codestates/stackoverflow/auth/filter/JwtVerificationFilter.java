package com.codestates.stackoverflow.auth.filter;

import com.codestates.stackoverflow.auth.provider.JwtProvider;
import com.codestates.stackoverflow.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* 토큰 검증 */
@Slf4j
public class JwtVerificationFilter extends OncePerRequestFilter {

    // JWT 검증 및 토큰의 정보
    private final JwtProvider jwtProvider;
    // 사용자 권한 생성용
    private final CustomAuthorityUtils authorityUtils;

    public JwtVerificationFilter(JwtProvider jwtProvider, CustomAuthorityUtils authorityUtils) {
        this.jwtProvider = jwtProvider;
        this.authorityUtils = authorityUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("[doFilterInternal] 필터 접근");

        String requestUri = request.getRequestURI();
        log.info("[doFilterInternal] Request URI : " + request.getRequestURI());

        if (requestUri.equals("/user/auth/reissue") || requestUri.equals("/user/login")) {
            // 로그인 또는 토큰 재발행 요청일 경우 아래 로직을 실행하지 않고 다음 필터로 넘김.
            log.info("[doFilterInternal] doFilter 실행");
            filterChain.doFilter(request, response);
        } else {
            log.info("[doFilterInternal] Jwt-Access 유효성 검증 시작");
            try {
                String base64EncodedSecretKey = jwtProvider.encodeBase64SecretKey(jwtProvider.getSecretKey());

                // 요청받은 request에서 token을 가져온다.
                String token = jwtProvider.getAccessTokenFromRequest(request);

                boolean isValidated = jwtProvider.verifySignature(token, base64EncodedSecretKey); // 토큰이 유효한지 검증한다.

                if (token != null && isValidated) { // Headers에 token이 담겨있고 유효하다면 Security Context Holder에 저장함.
                    Map<String, Object> claims = jwtProvider.getClaims(token, base64EncodedSecretKey).getBody();
                    log.info(claims.get("username").toString() + "의 인증정보 저장");
                    setAuthenticationToContext(claims);
                } else {
                    log.debug("유효한 JWT 토큰이 없습니다.");
                }
                filterChain.doFilter(request, response);

            } catch (ExpiredJwtException e) {
                log.error("ExpiredJwtException : " + e.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                request.setAttribute("ExpiredJwtException ", e);
            } catch (Exception e) {
                log.error("Exception : " + e.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                request.setAttribute("Exception ", e);
            }
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("access");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        log.info("[JwtVerificationFilter - verifyJws] request의 Jwt로 cliams 생성 시작");

        String jws = request.getHeader("access").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtProvider.encodeBase64SecretKey(jwtProvider.getSecretKey());
        Map<String, Object> claims = jwtProvider.getClaims(jws, base64EncodedSecretKey).getBody();

        log.info("[JwtVerificationFilter - verifyJws] request의 Jwt로 cliams 생성 완료");
        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        log.info("[JwtVerificationFilter - setAuthenticationToContext] request 정보로 권한 설정 시작");

        String username = (String) claims.get("username");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("[JwtVerificationFilter - setAuthenticationToContext] request 정보로 권한 설정 완료");
    }
}
