package com.jaks1m.project.domain.handler;

import com.jaks1m.project.dto.user.response.SocialUserDto;
import com.jaks1m.project.domain.common.jwt.JwtTokenProvider;
import com.jaks1m.project.domain.entity.token.RefreshToken;
import com.jaks1m.project.domain.common.UserRequestMapper;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.repository.auth.RedisRepository;
import com.jaks1m.project.repository.user.UserRepository;
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
    private final RedisRepository redisRepository;
    private final UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        SocialUserDto socialUserDto = userRequestMapper.toDto(oAuth2User);

        Optional<User> user = userRepository.findByEmail(socialUserDto.getEmail());
        if (user.isPresent()) {
            String accessToken = jwtTokenProvider.createAccessToken(user.get());
            RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(user.get());

            redisRepository.save(refreshToken);
            response.setStatus(200);
            response.sendRedirect(UriComponentsBuilder.fromUriString("https://jaks1m.netlify.app/")
                    .queryParam("accessToken",accessToken)
                    .queryParam("refreshToken",refreshToken.getValue()).toUriString());
        }
    }
}