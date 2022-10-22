package com.jaks1m.project.board.presentation.dto;

import com.jaks1m.project.board.domain.BoardOrderType;
import com.jaks1m.project.board.domain.BoardType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BoardTypeRequest {
    @NotNull
    private BoardType boardType;

    @NotNull
    private BoardOrderType boardOrderType;

}
