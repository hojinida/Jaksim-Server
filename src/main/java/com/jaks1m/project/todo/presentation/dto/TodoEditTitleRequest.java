package com.jaks1m.project.todo.presentation.dto;

import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
public class TodoEditTitleRequest {
    @NotEmpty
    @Column(length = 100)
    private String title;
}
