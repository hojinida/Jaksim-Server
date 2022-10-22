package com.jaks1m.project.todo.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class TodoCreateRequest {

    @Column(nullable = false,length = 100)
    private String title;
    @NotNull
    private Boolean completed;

    @Builder
    public TodoCreateRequest(String title, Boolean completed) {
        this.title = title;
        this.completed = completed;
    }
}
