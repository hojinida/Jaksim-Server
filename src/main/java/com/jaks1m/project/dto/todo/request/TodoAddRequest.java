package com.jaks1m.project.dto.todo.request;

import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class TodoAddRequest {

    @Column(nullable = false,length = 100)
    private String title;

    @NotNull
    private Boolean completed;
}
