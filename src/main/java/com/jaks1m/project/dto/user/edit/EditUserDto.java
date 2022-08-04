package com.jaks1m.project.dto.user.edit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.Column;


@Getter
@NoArgsConstructor
public class EditUserDto {
    @Nullable
    @Column(length = 30)
    private String name;

    private Boolean receivePolity;
}
