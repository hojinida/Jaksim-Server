package com.jaks1m.project.domain.service;

import com.jaks1m.project.domain.config.SecurityUtil;
import com.jaks1m.project.domain.dto.EditUserRequestDto;
import com.jaks1m.project.domain.dto.JoinUserRequestDto;
import com.jaks1m.project.domain.dto.UserDto;
import com.jaks1m.project.domain.dto.UserResponseDto;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.domain.jwt.JwtTokenProvider;
import com.jaks1m.project.domain.jwt.RefreshToken;
import com.jaks1m.project.domain.model.Role;
import com.jaks1m.project.domain.model.Status;
import com.jaks1m.project.domain.model.User;
import com.jaks1m.project.domain.repository.RedisRepository;
import com.jaks1m.project.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
    public UserDto join(JoinUserRequestDto request){
        validateDuplicateUser(request.getEmail());//중복 회원 검증
        User user = createUser(request);//회원 생성

        String accessToken = jwtTokenProvider.createAccessToken(user);
        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(user);

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
                .homeGround(user.getHomeGround())
                .role(user.getRole())
                .build();
    }
    @Transactional
    public void patchUser(EditUserRequestDto request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
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
    @Transactional
    public void logoutUser(HttpServletRequest request){
        String accessToken=jwtTokenProvider.resolveToken(request);

        if(StringUtils.hasText(accessToken)&&jwtTokenProvider.validateToken(accessToken)){
            String email= jwtTokenProvider.getUserEmail(accessToken);
            Optional<RefreshToken> refreshToken = redisRepository.findById(email);
            refreshToken.orElseThrow(()->new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED));

            redisRepository.deleteById(email);
            addBlacklist(accessToken);
        }else{
            log.error("로그아웃 실패");
            throw new CustomException(ErrorCode.JWT_UNAUTHORIZED);
        }
    }
    @Transactional
    public void quit(HttpServletRequest request){
        String accessToken=jwtTokenProvider.resolveToken(request);
        if(StringUtils.hasText(accessToken)&&jwtTokenProvider.validateToken(accessToken)) {
            User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                    .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
            user.updateStatus(Status.DELETE);
            redisRepository.deleteById(user.getEmail());
            addBlacklist(accessToken);
        }
    }
    //엑세스 토큰 블랙리스트 추가
    @Transactional
    public void addBlacklist(String accessToken){
        Date date=new Date();
        redisRepository.save(RefreshToken.builder()
                .key(accessToken)
                .expiredTime(jwtTokenProvider.getExpiration(accessToken).getTime()-(date.getTime()))
                .value("logout")
                .build());
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
                .role(Role.USER)
                .homeGround("web")
                .build();
    }
}
