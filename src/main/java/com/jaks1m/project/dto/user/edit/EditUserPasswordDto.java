package com.jaks1m.project.dto.user.edit;

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
