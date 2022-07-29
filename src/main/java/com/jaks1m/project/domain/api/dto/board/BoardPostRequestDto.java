package com.jaks1m.project.domain.api.dto.board;

import com.jaks1m.project.domain.api.entity.board.BoardType;
import com.jaks1m.project.domain.api.entity.user.User;
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
public class BoardPostRequestDto {
    @NotEmpty
    @Column(length = 100)
    private String title;
    @Lob
    @NotEmpty
    private String content;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BoardType boardType;
}
