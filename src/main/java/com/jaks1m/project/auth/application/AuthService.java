package com.jaks1m.project.auth.application;


import com.jaks1m.project.auth.presentation.dto.UserLoginRequest;
import com.jaks1m.project.user.application.dto.UserCreateResponse;
import com.jaks1m.project.common.exception.ErrorCode;
import com.jaks1m.project.common.exception.CustomException;
import com.jaks1m.project.auth.support.JwtTokenProvider;
import com.jaks1m.project.token.domain.RefreshToken;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.token.domain.repository.RefreshTokenRepository;
import com.jaks1m.project.user.domain.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserCreateResponse login(UserLoginRequest request){
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(user.isEmpty()){
            log.info("일치하는 회원이 없습니다.");
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        // 보내온 비밀번호가 데이터베이스에 저장된 비밀번호와 일치하는지 확인합니다
        if(!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            log.info("패스워드가 일치하지 않습니다.");
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.get());
        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(user.get());

        refreshTokenRepository.save(refreshToken);

        return UserCreateResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public UserCreateResponse reissue(HttpServletRequest request) {
        try {
            String accessToken = jwtTokenProvider.resolveToken(request);
            String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

            String email= jwtTokenProvider.getUserEmail(accessToken);

            // Redis에 저장된 Refresh Token을 찾고 만일 없다면 401 에러를 내려줍니다
            RefreshToken findRefreshToken = refreshTokenRepository.findById(email)
                    .orElseThrow(()->new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED));

            if(Objects.equals(refreshToken, findRefreshToken.getValue())) {
                // Refresh Token이 만료가 되지 않은 경우
                Optional<User> user = userRepository.findByEmail(email);

                if (user.isPresent()) {
                    // Access Token과 Refresh Token을 둘 다 새로 발급하여 Refresh Token은 새로 Redis에 저장
                    String newAccessToken = jwtTokenProvider.createAccessToken(user.get());
                    RefreshToken newRefreshToken = jwtTokenProvider.createRefreshToken(user.get());
                    refreshTokenRepository.save(newRefreshToken);

                    return UserCreateResponse.builder()
                            .accessToken(newAccessToken)
                            .refreshToken(newRefreshToken)
                            .build();
                }
            }else{
                throw new CustomException(ErrorCode.JWT_UNAUTHORIZED);
            }
        } catch(ExpiredJwtException e) {
            // Refresh Token 만료 Exception
            log.info(e.getMessage());
            throw new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
        }
        return null;
    }
}
