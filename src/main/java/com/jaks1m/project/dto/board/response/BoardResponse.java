package com.jaks1m.project.dto.board.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponse {
    private Long boardId;
    private String userName;
    private String title;
    private String content;
    private Long visit;

    private LocalDateTime createdData;

    private LocalDateTime lastModifiedDate;
    @Builder
    public BoardResponse(Long boardId, String userName, String title, String content, Long visit, LocalDateTime createdData, LocalDateTime lastModifiedDate) {
        this.boardId = boardId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.visit = visit;
        this.createdData = createdData;
        this.lastModifiedDate = lastModifiedDate;
    }
}
