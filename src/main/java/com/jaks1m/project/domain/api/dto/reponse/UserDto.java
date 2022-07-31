package com.jaks1m.project.domain.api.dto.reponse;

import com.jaks1m.project.domain.oauth.token.RefreshToken;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public UserDto(String accessToken, RefreshToken refreshToken){
        this.accessToken=accessToken;
        this.refreshToken=refreshToken.getValue();
    }
}
