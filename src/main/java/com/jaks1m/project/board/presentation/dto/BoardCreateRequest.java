package com.jaks1m.project.board.presentation.dto;

import com.jaks1m.project.board.domain.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class BoardCreateRequest {
    @NotEmpty
    @Column(length = 100)
    private String title;
    @NotEmpty
    private String bracket;
    @Lob
    @NotEmpty
    private String content;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BoardType boardType;
}
