package com.jaks1m.project.domain.oauth.service;

import com.jaks1m.project.domain.oauth.info.OAuth2Attribute;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.domain.oauth.model.Role;
import com.jaks1m.project.domain.api.entity.user.User;
import com.jaks1m.project.domain.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("OAuth2UserService Start");
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        //서비스 구분 naver gogle kakao 등
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        var userAttribute = oAuth2Attribute.convertToMap();

        Optional<User> findUser=userRepository.findByEmail(oAuth2Attribute.getEmail());
        if(findUser.isEmpty()) {
            userRepository.save(createUser(oAuth2Attribute,registrationId));
        } else if(registrationId.equals("WEB")){
            throw new CustomException(ErrorCode.ALREADY_WEB_JOIN);
        } else if (!Objects.equals(findUser.get().getHomeGround(), registrationId)) {
            throw new CustomException(ErrorCode.ALREADY_JOIN);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                userAttribute, "email");
    }

    private User createUser(OAuth2Attribute oAuth2Attribute,String registrationId){
        return User.builder()
                .email(oAuth2Attribute.getEmail())
                .name(oAuth2Attribute.getName())
                .password(oAuth2Attribute.getAttributeKey())
                .privacyPolity(true)
                .termsOfService(true)
                .receivePolity(null)
                .role(Role.USER)
                .homeGround(registrationId)
                .build();
    }
}
