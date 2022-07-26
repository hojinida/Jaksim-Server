package com.jaks1m.project.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UserEmailRequestDto {
    @NotEmpty(message = "이메일 입력값이 존재하지 않습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
}
