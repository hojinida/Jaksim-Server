package com.jaks1m.project.dto.todo;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class EditTodoCompletedRequestDto {
    @NotNull
    private Boolean completed;
}
