package com.jaks1m.project.schedule.presentation.dto;

import lombok.Getter;
import javax.validation.constraints.NotEmpty;


@Getter
public class ScheduleEditContentRequest {
    @NotEmpty
    private String Content;
}
