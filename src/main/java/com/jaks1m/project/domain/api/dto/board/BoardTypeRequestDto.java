package com.jaks1m.project.domain.api.dto.board;

import com.jaks1m.project.domain.api.entity.board.BoardOrderType;
import com.jaks1m.project.domain.api.entity.board.BoardType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BoardTypeRequestDto {
    @NotNull
    private BoardType boardType;

    @NotNull
    private BoardOrderType boardOrderType;

}
