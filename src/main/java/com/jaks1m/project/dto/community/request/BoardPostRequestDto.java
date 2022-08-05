package com.jaks1m.project.dto.community.request;

import com.jaks1m.project.domain.entity.community.BoardType;
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
