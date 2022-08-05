package com.jaks1m.project.dto.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetTodoResponseDto {
    private Long id;
    private String title;
    private boolean completed;

    @Builder
    public GetTodoResponseDto(Long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }
}
