package com.jaks1m.project.user.support;

import com.jaks1m.project.user.presentation.dto.UserSocialCreateRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public UserSocialCreateRequest toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return UserSocialCreateRequest.builder()
                .email((String)attributes.get("email"))
                .name((String)attributes.get("name"))
                .picture((String)attributes.get("picture"))
                .build();
    }
}
