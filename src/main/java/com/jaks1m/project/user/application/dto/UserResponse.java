package com.jaks1m.project.user.application.dto;

import com.jaks1m.project.user.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {
    private String email;
    private String name;
    private String image;
    private Role role;
    private Boolean privacyPolity;
    private Boolean termsOfService;
    private Boolean receivePolity;

    @Builder
    public UserResponse(String email, String name, String image, Role role, Boolean privacyPolity, Boolean termsOfService, Boolean receivePolity) {
        this.email = email;
        this.name = name;
        this.image = image;
        this.role = role;
        this.privacyPolity = privacyPolity;
        this.termsOfService = termsOfService;
        this.receivePolity = receivePolity;
    }
}
