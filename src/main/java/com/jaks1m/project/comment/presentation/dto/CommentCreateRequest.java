package com.jaks1m.project.comment.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {
    @NotEmpty
    private String comment;
}
