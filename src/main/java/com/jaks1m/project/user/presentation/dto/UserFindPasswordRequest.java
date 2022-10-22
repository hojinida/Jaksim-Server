package com.jaks1m.project.user.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UserFindPasswordRequest {
    @NotEmpty
    private String password;
}
