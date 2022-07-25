package com.jaks1m.project.domain.handler;

import com.jaks1m.project.domain.dto.SocialUserDto;
import com.jaks1m.project.domain.dto.UserDto;
import com.jaks1m.project.domain.jwt.JwtTokenProvider;
import com.jaks1m.project.domain.jwt.Token;
import com.jaks1m.project.domain.mapper.UserRequestMapper;
import com.jaks1m.project.domain.model.User;
import com.jaks1m.project.domain.repository.RedisRepository;
import com.jaks1m.project.domain.repository.UserRepository;
import com.jaks1m.project.domain.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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
            Token accessToken = jwtTokenProvider.createAccessToken(user.get());
            Token refreshToken=jwtTokenProvider.createRefreshToken(user.get());

            redisRepository.save(refreshToken);

            makeRedirectUrl(UserDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build());
        }
    }

    private ResponseEntity<BaseResponse> makeRedirectUrl (UserDto userDto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.builder()
                        .status(200)
                        .body(userDto)
                        .build());
    }
}