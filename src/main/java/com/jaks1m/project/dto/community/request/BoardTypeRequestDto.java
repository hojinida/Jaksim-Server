package com.jaks1m.project.dto.community.request;

import com.jaks1m.project.domain.entity.community.BoardOrderType;
import com.jaks1m.project.domain.entity.community.BoardType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BoardTypeRequestDto {
    @NotNull
    private BoardType boardType;

    @NotNull
    private BoardOrderType boardOrderType;

}
