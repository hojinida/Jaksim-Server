package com.jaks1m.project.user.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.persistence.Column;


@Getter
@NoArgsConstructor
public class UserEditRequest {
    @Nullable
    @Column(length = 30)
    private String name;

    private Boolean receivePolity;
}
