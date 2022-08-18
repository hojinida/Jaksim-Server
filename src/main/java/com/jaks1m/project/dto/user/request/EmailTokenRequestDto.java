package com.jaks1m.project.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class EmailTokenRequestDto {
    @NotEmpty
    private String token;
}
