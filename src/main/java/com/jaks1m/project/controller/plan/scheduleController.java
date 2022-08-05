package com.jaks1m.project.controller.plan;

import com.jaks1m.project.domain.common.response.BaseResponse;
import com.jaks1m.project.dto.schedule.AddScheduleRequestDto;
import com.jaks1m.project.dto.schedule.GetScheduleResponseDto;
import com.jaks1m.project.service.func.ScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping
    @ApiOperation(value = "schedule 조회")
    public ResponseEntity<BaseResponse<List<GetScheduleResponseDto>>> addTodo(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate localDate){
        return ResponseEntity.status(200).
                body(BaseResponse.<List<GetScheduleResponseDto>>builder()
                        .message("schedule 조회 성공")
                        .body(scheduleService.getSchedule(localDate)).build());
    }
}
