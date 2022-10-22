package com.jaks1m.project.board.application.dto;

import com.jaks1m.project.comment.application.dto.CommentResponse;
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
    private String bracket;
    private String content;
    private Long visits;
    private Long hearts;
    private List<String> images;
    private LocalDateTime createdData;
    private LocalDateTime lastModifiedDate;
    private List<CommentResponse> comments;

    @Builder
    public BoardResponse(Long boardId, String userName, String title, String bracket, String content, Long visits, Long hearts, List<String> images, List<CommentResponse> comments, LocalDateTime createdData, LocalDateTime lastModifiedDate) {
        this.boardId = boardId;
        this.userName = userName;
        this.title = title;
        this.bracket=bracket;
        this.content = content;
        this.visits = visits;
        this.hearts=hearts;
        this.images = images;
        this.comments = comments;
        this.createdData = createdData;
        this.lastModifiedDate = lastModifiedDate;
    }
}
