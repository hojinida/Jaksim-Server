package com.jaks1m.project.auth.support.handler;

import com.jaks1m.project.user.presentation.dto.UserSocialCreateRequest;
import com.jaks1m.project.auth.support.JwtTokenProvider;
import com.jaks1m.project.token.domain.RefreshToken;
import com.jaks1m.project.user.support.UserRequestMapper;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.token.domain.repository.RefreshTokenRepository;
import com.jaks1m.project.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRequestMapper userRequestMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserSocialCreateRequest userSocialCreateRequest = userRequestMapper.toDto(oAuth2User);

        Optional<User> user = userRepository.findByEmail(userSocialCreateRequest.getEmail());
        if (user.isPresent()) {
            String accessToken = jwtTokenProvider.createAccessToken(user.get());
            RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(user.get());

            refreshTokenRepository.save(refreshToken);
            response.setStatus(200);
            response.sendRedirect(UriComponentsBuilder.fromUriString("https://jaks1m.netlify.app/oauth/redirect")
                    .queryParam("accessToken",accessToken)
                    .queryParam("refreshToken",refreshToken.getValue()).toUriString());
        }
    }
}