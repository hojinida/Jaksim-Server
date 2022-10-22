package com.jaks1m.project.email.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class EmailTokenRequest {
    @NotEmpty
    private String token;
}
