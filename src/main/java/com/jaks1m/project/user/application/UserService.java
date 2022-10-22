package com.jaks1m.project.user.application;


import com.jaks1m.project.aws.application.dto.ImageDto;
import com.jaks1m.project.user.presentation.dto.UserEditRequest;
import com.jaks1m.project.user.presentation.dto.UserEditPasswordRequest;
import com.jaks1m.project.aws.domain.S3Image;
import com.jaks1m.project.auth.support.SecurityUtil;
import com.jaks1m.project.user.presentation.dto.UserFindPasswordRequest;
import com.jaks1m.project.user.presentation.dto.UserCreateRequest;
import com.jaks1m.project.user.application.dto.UserCreateResponse;
import com.jaks1m.project.user.application.dto.UserResponse;
import com.jaks1m.project.common.exception.ErrorCode;
import com.jaks1m.project.common.exception.CustomException;
import com.jaks1m.project.auth.support.JwtTokenProvider;
import com.jaks1m.project.token.domain.RefreshToken;
import com.jaks1m.project.user.domain.Role;
import com.jaks1m.project.user.domain.Status;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.token.domain.repository.RefreshTokenRepository;
import com.jaks1m.project.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    //회원 가입
    @Transactional
    public UserCreateResponse joinUser(UserCreateRequest request){
        validateDuplicateUser(request.getEmail());//중복 회원 검증
        User user = createUser(request);//회원 생성

        String accessToken = jwtTokenProvider.createAccessToken(user);
        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(user);

        refreshTokenRepository.save(refreshToken);

        userRepository.save(user);
        return UserCreateResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    @Transactional
    public void editPassword(UserEditPasswordRequest request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        if(!passwordEncoder.matches(request.getBeforePassword(),user.getPassword())){
            throw new CustomException(ErrorCode.NOT_EQUAL_PASSWORD);
        }
        user.updatePassword(passwordEncoder.encode(request.getAfterPassword()));
    }
    public UserResponse findUser(){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));

        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName().getName())
                .image(user.getS3Image().getImagePath())
                .role(user.getRole())
                .privacyPolity(user.getPrivacyPolity().getPrivacyPolity())
                .termsOfService(user.getTermsOfService().getTermsOfService())
                .receivePolity(user.getReceivePolity().getReceivePolity())
                .build();
    }

    @Transactional
    public void patchUser(UserEditRequest request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        if(request.getName()!=null){
            user.updateName(request.getName());
        }
        user.updateReceivePolity(request.getReceivePolity());
    }
    @Transactional
    public void logoutUser(HttpServletRequest request){
        String accessToken=jwtTokenProvider.resolveToken(request);
        String refreshToken=jwtTokenProvider.resolveRefreshToken(request);

        String email = jwtTokenProvider.getUserEmail(accessToken);

        RefreshToken findRefreshToken = refreshTokenRepository.findById(email)
                .orElseThrow(()->new CustomException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED));
        if(!Objects.equals(refreshToken, findRefreshToken.getValue())) {
            refreshTokenRepository.deleteById(findRefreshToken.getKey());
            addBlacklist(accessToken);
        }else{
            throw new CustomException(ErrorCode.JWT_UNAUTHORIZED);
        }
    }
    @Transactional
    public void findPassword(UserFindPasswordRequest request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        user.updatePassword(passwordEncoder.encode(request.getPassword()));
    }
    @Transactional
    public void quitUser(HttpServletRequest request){
        String accessToken=jwtTokenProvider.resolveToken(request);
        if(StringUtils.hasText(accessToken)&&jwtTokenProvider.validateToken(accessToken)) {
            User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                    .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
            user.updateStatus(Status.DELETE);
            refreshTokenRepository.deleteById(user.getEmail());
            addBlacklist(accessToken);
        }
    }
    //엑세스 토큰 블랙리스트 추가
    @Transactional
    public void addBlacklist(String accessToken){
        Date date=new Date();
        refreshTokenRepository.save(RefreshToken.builder()
                .key(accessToken)
                .expiredTime(jwtTokenProvider.getExpiration(accessToken).getTime()-(date.getTime()))
                .value("logout")
                .build());
    }

    @Transactional
    public void addImage(List<ImageDto> imageDto){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        imageDto.forEach(imageDto1 -> user.updateImage(imageDto1.getKey(),imageDto1.getPath()));
    }

    @Transactional
    public void deleteImage(){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        user.updateImage(null,null);
    }
    private void validateDuplicateUser(String email){
        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isPresent()) {
            log.info("이미 회원가입 한 이메일 입니다.");
            throw new CustomException(ErrorCode.ALREADY_JOIN);
        }
    }
    private User createUser(UserCreateRequest request){
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        return User.builder().email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .privacyPolity(request.getPrivacyPolity())
                .termsOfService(request.getTermsOfService())
                .receivePolity(request.getReceivePolity())
                .role(Role.USER)
                .homeGround("web")
                .s3Image(new S3Image())
                .build();
    }
}
