package com.jaks1m.project.domain.oauth.mapper;

import com.jaks1m.project.domain.api.dto.SocialUserDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public SocialUserDto toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return SocialUserDto.builder()
                .email((String)attributes.get("email"))
                .name((String)attributes.get("name"))
                //.picture((String)attributes.get("picture"))
                .build();
    }

//    public UserFindRequest toFindDto(SocialUserDto userDto) {
//        return new UserFindRequest(userDto.getEmail());
//    }
}
