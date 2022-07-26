package com.jaks1m.project.domain.oauth.handler;

import com.jaks1m.project.domain.api.dto.SocialUserDto;
import com.jaks1m.project.domain.oauth.model.JwtTokenProvider;
import com.jaks1m.project.domain.oauth.token.RefreshToken;
import com.jaks1m.project.domain.oauth.mapper.UserRequestMapper;
import com.jaks1m.project.domain.api.entity.user.User;
import com.jaks1m.project.domain.api.repository.RedisRepository;
import com.jaks1m.project.domain.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        SocialUserDto socialUserDto = userRequestMapper.toDto(oAuth2User);

        Optional<User> user = userRepository.findByEmail(socialUserDto.getEmail());
        if(user.isPresent()) {
            String accessToken = jwtTokenProvider.createAccessToken(user.get());
            RefreshToken refreshToken=jwtTokenProvider.createRefreshToken(user.get());

            redisRepository.save(refreshToken);

            String uri=makeRedirectUrl(accessToken,refreshToken);
            response.sendRedirect(uri);
        }
    }

    private String makeRedirectUrl (String accessToken,RefreshToken refreshToken){
        return UriComponentsBuilder.fromUriString("http//:localhost:8080")
                .queryParam("accessToken",accessToken)
                .queryParam("refreshToken",refreshToken.getValue())
                .build().toUriString();
    }
}