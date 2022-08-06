package com.jaks1m.project.dto.schedule;

import lombok.Getter;
import javax.validation.constraints.NotEmpty;


@Getter
public class EditScheduleContentRequestDto {
    @NotEmpty
    private String Content;
}
