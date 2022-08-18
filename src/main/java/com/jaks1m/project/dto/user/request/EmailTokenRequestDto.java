package com.jaks1m.project.dto.user.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class EmailTokenRequestDto {
    @NotEmpty
    private String token;
}
