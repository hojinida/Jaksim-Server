package com.jaks1m.project.schedule.presentation;

import com.jaks1m.project.common.domain.BaseResponse;
import com.jaks1m.project.schedule.presentation.dto.ScheduleCreateRequest;
import com.jaks1m.project.schedule.presentation.dto.ScheduleEditContentRequest;
import com.jaks1m.project.schedule.presentation.dto.ScheduleEditTimeRequest;
import com.jaks1m.project.schedule.application.dto.ScheduleResponse;
import com.jaks1m.project.schedule.application.ScheduleService;
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
    public ResponseEntity<String> addTodo(@RequestBody @Validated ScheduleCreateRequest request){
        scheduleService.addSchedule(request);
        return ResponseEntity.status(200).body("schedule 등록 성공");
    }

    @GetMapping
    @ApiOperation(value = "schedule 조회")
    public ResponseEntity<BaseResponse<List<ScheduleResponse>>> addTodo(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate localDate){
        return ResponseEntity.status(200).
                body(BaseResponse.<List<ScheduleResponse>>builder()
                        .status(200)
                        .message("schedule 조회 성공")
                        .body(scheduleService.getSchedule(localDate)).build());
    }

    @PatchMapping("/{id}/time")
    @ApiOperation(value = "schedule 시간 수정")
    public ResponseEntity<String> editTodoTime(@RequestBody @Validated ScheduleEditTimeRequest request, @PathVariable Long id){
        scheduleService.editScheduleTime(request,id);
        return ResponseEntity.status(200).body("schedule 시간 수정 성공");
    }

    @PatchMapping("/{id}/content")
    @ApiOperation(value = "schedule 내용 수정")
    public ResponseEntity<String> editTodoContent(@RequestBody @Validated ScheduleEditContentRequest request, @PathVariable Long id){
        scheduleService.editScheduleContent(request,id);
        return ResponseEntity.status(200).body("schedule 내용 수정 성공");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "schedule 삭제")
    public ResponseEntity<String> deleteTodoContent(@PathVariable Long id){
        scheduleService.deleteScheduleContent(id);
        return ResponseEntity.status(200).body("schedule 삭제 성공");
    }
}
