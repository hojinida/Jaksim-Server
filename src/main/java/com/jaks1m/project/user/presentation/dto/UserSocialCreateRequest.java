package com.jaks1m.project.user.presentation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSocialCreateRequest {
    private String email;
    private String name;

    private String picture;
    @Builder
    public UserSocialCreateRequest(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture=picture;
    }
}
