package com.jaks1m.project.user.application.dto;

import com.jaks1m.project.token.domain.RefreshToken;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserCreateResponse {
    private String accessToken;
    private String refreshToken;
    @Builder
    public UserCreateResponse(String accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken.getValue();
    }
}
