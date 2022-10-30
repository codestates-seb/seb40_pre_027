package com.codestates.stackoverflow.auth.filter;

import com.codestates.stackoverflow.auth.provider.JwtProvider;
import com.codestates.stackoverflow.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
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

/* request의 토큰 검증 필터 */
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

    // 처음에 해야 할 일
    //
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("request URI : "+request.getRequestURI());

        if (request.getRequestURI().startsWith("/auth/reissue")) {
            // Access Token 재발급 URI 요청 시 아래 로직을 거치지 않고 바로 다음 Filter로 넘김.
            filterChain.doFilter(request, response);
        }
        log.info("[JwtVerificationFilter - doFilterInternal] request 로 들어오는 Jwt 유효성 검증 시작");
        try {
            String base64EncodedSecretKey = jwtProvider.encodeBase64SecretKey(jwtProvider.getSecretKey());
            String token = jwtProvider.getAccessTokenFromRequest(request); // 요청받은 request에서 token을 가져온다.
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
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        log.info("[JwtVerificationFilter - verifyJws] request의 Jwt로 cliams 생성 시작");

        String jws = request.getHeader("Authorization").replace("Bearer ", "");
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
