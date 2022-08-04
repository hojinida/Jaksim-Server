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
    private String homeGround;
    private Role role;


    @Builder
    public UserResponseDto(String email, String name,String homeGround ,Role role) {
        this.email = email;
        this.name = name;
        this.homeGround=homeGround;
        this.role = role;
    }
}
