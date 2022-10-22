package com.jaks1m.project.schedule.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ScheduleResponse {
    private Long id;
    private LocalTime start;
    private LocalTime end;
    private String content;
    @Builder
    public ScheduleResponse(Long id, LocalTime start, LocalTime end, String content) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.content = content;
    }
}
