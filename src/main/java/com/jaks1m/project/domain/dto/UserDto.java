package com.jaks1m.project.domain.dto;

import com.jaks1m.project.domain.jwt.RefreshToken;
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
