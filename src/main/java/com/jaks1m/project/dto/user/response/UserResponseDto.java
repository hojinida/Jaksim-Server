package com.jaks1m.project.dto.user.response;

import com.jaks1m.project.domain.entity.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String name;
    private String image;
    private Role role;
    private Boolean privacyPolity;
    private Boolean termsOfService;
    private Boolean receivePolity;

    @Builder
    public UserResponseDto(String email, String name, String image, Role role, Boolean privacyPolity, Boolean termsOfService, Boolean receivePolity) {
        this.email = email;
        this.name = name;
        this.image = image;
        this.role = role;
        this.privacyPolity = privacyPolity;
        this.termsOfService = termsOfService;
        this.receivePolity = receivePolity;
    }
}
