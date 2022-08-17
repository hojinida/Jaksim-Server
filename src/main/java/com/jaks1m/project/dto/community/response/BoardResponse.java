package com.jaks1m.project.dto.community.response;

import com.jaks1m.project.domain.entity.community.Heart;
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
    private Long visits;
    private Long hearts;
    private List<String> images;
    private LocalDateTime createdData;
    private LocalDateTime lastModifiedDate;
    private List<CommentResponseDto> comments;

    @Builder
    public BoardResponse(Long boardId, String userName, String title, String content, Long visits,Long hearts, List<String> images, List<CommentResponseDto> comments, LocalDateTime createdData, LocalDateTime lastModifiedDate) {
        this.boardId = boardId;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.visits = visits;
        this.hearts=hearts;
        this.images = images;
        this.comments = comments;
        this.createdData = createdData;
        this.lastModifiedDate = lastModifiedDate;
    }
}
