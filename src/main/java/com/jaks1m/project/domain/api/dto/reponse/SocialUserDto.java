package com.jaks1m.project.domain.api.dto.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialUserDto {
    private String email;
    private String name;
    @Builder
    public SocialUserDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
