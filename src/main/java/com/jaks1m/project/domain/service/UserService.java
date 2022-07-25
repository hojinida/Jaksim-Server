package com.jaks1m.project.domain.service;

import com.jaks1m.project.domain.config.SecurityUtil;
import com.jaks1m.project.domain.dto.EditUserRequestDto;
import com.jaks1m.project.domain.dto.JoinUserRequestDto;
import com.jaks1m.project.domain.dto.UserDto;
import com.jaks1m.project.domain.dto.UserResponseDto;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.domain.jwt.JwtTokenProvider;
import com.jaks1m.project.domain.jwt.Token;
import com.jaks1m.project.domain.model.Role;
import com.jaks1m.project.domain.model.User;
import com.jaks1m.project.domain.repository.RedisRepository;
import com.jaks1m.project.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRepository redisRepository;

    //회원 가입
    @Transactional
    public UserDto join(JoinUserRequestDto request, HttpServletResponse response){
        validateDuplicateUser(request.getEmail());//중복 회원 검증
        User user = createUser(request);//회원 생성

        Token accessToken = jwtTokenProvider.createAccessToken(user);
        Token refreshToken = jwtTokenProvider.createRefreshToken(user);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken.getValue());
        redisRepository.save(refreshToken);

        userRepository.save(user);
        return UserDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    @Transactional
    public UserResponseDto findUser(){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));

        return UserResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName().getName())
                .role(user.getRole())
                .build();
    }
    @Transactional

    public void patchUser(EditUserRequestDto request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        System.out.println(user.getUsername());
        if(request.getEmail()!=null && validateDuplicateUser(request.getEmail())){
            user.updateEmail(request.getEmail());
        }
        if(request.getPassword()!=null){
            user.updatePassword(passwordEncoder.encode(request.getPassword()));
        }
        if(request.getName()!=null){
            user.updateName(request.getName());
        }
    }

    private Boolean validateDuplicateUser(String email){
        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isPresent()){
            log.info("이미 회원가입 한 이메일 입니다.");
            throw new CustomException(ErrorCode.ALREADY_JOIN);
        }
        return true;
    }
    private User createUser(JoinUserRequestDto request){
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return User.builder().email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .privacyPolity(request.getPrivacyPolity())
                .termsOfService(request.getTermsOfService())
                .receivePolity(request.getReceivePolity())
                .enabled(true)
                .role(Role.USER)
                .wSigned("web")
                .build();
    }
}
