package com.jaks1m.project.comment.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String name;
    private String image;
    private String comment;
    private LocalDateTime createdData;
    private LocalDateTime lastModifiedDate;
    @Builder
    public CommentResponse(Long id, String name, String image, String comment, LocalDateTime createdData, LocalDateTime lastModifiedDate) {
        this.id=id;
        this.name = name;
        this.image = image;
        this.comment = comment;
        this.createdData = createdData;
        this.lastModifiedDate = lastModifiedDate;
    }
}
