package com.jaks1m.project.dto.todo;

import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
public class EditTodoTitleRequestDto {
    @NotEmpty
    @Column(length = 100)
    private String title;
}
