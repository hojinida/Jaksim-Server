package com.jaks1m.project.domain.dto;

import com.jaks1m.project.domain.jwt.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public UserDto(Token accessToken,Token refreshToken){
        this.accessToken=accessToken.getValue();
        this.refreshToken=refreshToken.getValue();
    }
}
