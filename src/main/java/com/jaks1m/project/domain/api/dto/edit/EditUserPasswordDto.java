package com.jaks1m.project.domain.api.dto.edit;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class EditUserPasswordDto {
    @NotEmpty
    private String beforePassword;
    @NotEmpty
    private String afterPassword;
}
