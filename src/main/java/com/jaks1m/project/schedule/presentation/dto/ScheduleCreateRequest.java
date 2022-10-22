package com.jaks1m.project.schedule.presentation.dto;


import lombok.Getter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
public class ScheduleCreateRequest {
    @NotNull
    private LocalTime start;
    @NotNull
    private LocalTime end;
    @NotEmpty
    private String content;
}
