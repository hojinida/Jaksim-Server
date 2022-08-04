package com.jaks1m.project.dto.board.request;

import com.jaks1m.project.domain.entity.board.BoardOrderType;
import com.jaks1m.project.domain.entity.board.BoardType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BoardTypeRequestDto {
    @NotNull
    private BoardType boardType;

    @NotNull
    private BoardOrderType boardOrderType;

}
