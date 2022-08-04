package com.jaks1m.project.controller.todo;

import com.jaks1m.project.domain.common.response.BaseResponse;
import com.jaks1m.project.dto.todo.TodoDto;
import com.jaks1m.project.service.func.TodoService;
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
@RequestMapping("/api/v1/todo")
public class TodoController {
    private final TodoService todoService;
    @PostMapping
    @ApiOperation(value = "todo 등록")
    public ResponseEntity<String> addTodo(@RequestBody @Validated TodoDto request){
        todoService.addTodo(request);
        return ResponseEntity.status(200).body("Todo 등록 성공");
    }

    @GetMapping
    @ApiOperation(value = "todo 조회")
    public ResponseEntity<BaseResponse<List<TodoDto>>> getTodo(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate){
        return ResponseEntity.status(200)
                .body(BaseResponse.<List<TodoDto>>builder()
                        .status(200)
                        .message("Todo 조회 성공")
                        .body(todoService.getTodo(localDate)).build());
    }
}
