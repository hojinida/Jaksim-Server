package com.jaks1m.project.dto.schedule;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
public class EditScheduleTimeRequestDto {
    @NotNull
    private LocalTime start;
    @NotNull
    private LocalTime end;
}
