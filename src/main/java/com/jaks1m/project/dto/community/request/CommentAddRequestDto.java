package com.jaks1m.project.dto.community.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class CommentAddRequestDto {
    @NotEmpty
    private String comment;
}
