package com.jaks1m.project.domain.jwt;

import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = tokenProvider.resolveToken(request);
            if (StringUtils.hasText(accessToken)&&tokenProvider.validateToken(accessToken)) {
                this.setAuthentication(accessToken);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.info("엑세스 토큰 만료.");
            throw new CustomException(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
        }
    }

    // SecurityContext에 Authentication 저장
    private void setAuthentication(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
