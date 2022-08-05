package com.jaks1m.project.controller.plan;

import com.jaks1m.project.dto.schedule.AddScheduleRequestDto;
import com.jaks1m.project.service.func.ScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class scheduleController {
    private final ScheduleService scheduleService;
    @PostMapping
    @ApiOperation(value = "schedule 등록")
    public ResponseEntity<String> addTodo(@RequestBody @Validated AddScheduleRequestDto request){
        scheduleService.addSchedule(request);
        return ResponseEntity.status(200).body("schedule 등록 성공");
    }
}
