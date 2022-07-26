package com.jaks1m.project.user.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    @NotEmpty(message = "이메일 입력값이 존재하지 않습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotNull(message = "비밀번호 입력값이 존재하지 않습니다.")
    private String password;

    @NotEmpty(message = "이름 입력값이 존재하지 않습니다.")
    private String name;

    @AssertTrue(message = "서비스 이용약관 동의가 거부되었습니다.")
    private Boolean termsOfService;

    @AssertTrue(message = "개인정보 이용 동의가 거부되었습니다.")
    private Boolean privacyPolity;

    private Boolean receivePolity;
}
