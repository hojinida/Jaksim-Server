package com.jaks1m.project.domain.api.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponse {
    private Long boardId;
    private String userName;
    private String title;
    private String content;
    private Long visit;

    @Builder
    public BoardResponse(Long boardId, String userName, String title, String content, Long visit) {
        this.boardId = boardId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.visit = visit;
    }
}
