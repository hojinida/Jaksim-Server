package com.jaks1m.project.dto.schedule;


import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
public class AddScheduleRequestDto {
    @NotNull
    private LocalTime start;
    @NotNull
    private LocalTime end;
    @NotEmpty
    private String content;
}
