package com.jaks1m.project.dto.community.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponse {
    private Long boardId;
    private String userName;
    private String title;
    private String content;
    private Long visit;

    private List<String> images;

    private LocalDateTime createdData;

    private LocalDateTime lastModifiedDate;
    @Builder
    public BoardResponse(Long boardId, String userName, String title, String content, Long visit, List<String> images, LocalDateTime createdData, LocalDateTime lastModifiedDate) {
        this.boardId = boardId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.visit = visit;
        this.images = images;
        this.createdData = createdData;
        this.lastModifiedDate = lastModifiedDate;
    }
}
