package com.jaks1m.project.domain.api.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class LoginUserRequestDto {
    @NotEmpty(message = "이메일 입력값이 존재하지 않습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty(message = "비밀번호 입력값이 존재하지 않습니다.")
    private String password;
    @Builder
    public LoginUserRequestDto(String email,String password){
        this.email=email;
        this.password=password;
    }
}
