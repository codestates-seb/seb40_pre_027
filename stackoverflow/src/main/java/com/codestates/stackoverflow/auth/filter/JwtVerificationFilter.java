package com.codestates.stackoverflow.auth.filter;

import com.codestates.stackoverflow.auth.provider.JwtProvider;
import com.codestates.stackoverflow.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
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
import java.security.SignatureException;
import java.util.List;
import java.util.Map;

/* request의 토큰 검사 클래스 */
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
        log.info("[JwtVerificationFilter - doFilterInternal] request 로 들어오는 Jwt 유효성 검증 시작");
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
//        }
//        catch (SignatureException signatureException) {
//            request.setAttribute("exception", signatureException);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }


        filterChain.doFilter(request, response);
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
