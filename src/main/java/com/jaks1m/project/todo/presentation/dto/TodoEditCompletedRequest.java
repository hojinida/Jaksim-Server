package com.jaks1m.project.todo.presentation.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class TodoEditCompletedRequest {
    @NotNull
    private Boolean completed;
}
