package com.jaks1m.project.domain.api.dto.edit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.Column;
import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class EditUserRequestDto {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Nullable
    private String email;
    @Nullable
    private String password;
    @Nullable
    @Column(length = 30)
    private String name;

    @Builder
    public EditUserRequestDto(@Nullable String email, @Nullable String password, @Nullable String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
