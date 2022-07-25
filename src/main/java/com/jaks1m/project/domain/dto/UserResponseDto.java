package com.jaks1m.project.domain.dto;

import com.jaks1m.project.domain.model.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String name;
    private Role role;

    @Builder
    public UserResponseDto(String email, String name, Role role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }
}
