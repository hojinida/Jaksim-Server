package com.jaks1m.project.controller.todo;

import com.jaks1m.project.domain.common.response.BaseResponse;
import com.jaks1m.project.dto.todo.EditTodoTitleRequestDto;
import com.jaks1m.project.dto.todo.AddTodoRequestDto;
import com.jaks1m.project.dto.todo.GetTodoResponseDto;
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
    public ResponseEntity<String> addTodo(@RequestBody @Validated AddTodoRequestDto request){
        todoService.addTodo(request);
        return ResponseEntity.status(200).body("Todo 등록 성공");
    }

    @GetMapping
    @ApiOperation(value = "todo 조회")
    public ResponseEntity<BaseResponse<List<GetTodoResponseDto>>> getTodo(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate){
        return ResponseEntity.status(200)
                .body(BaseResponse.<List<GetTodoResponseDto>>builder()
                        .status(200)
                        .message("Todo 조회 성공")
                        .body(todoService.getTodo(localDate)).build());
    }

    @PatchMapping("/{id}/title")
    @ApiOperation(value = "todo 내용 수정")
    public ResponseEntity<String> editTodoTitle(@RequestBody @Validated EditTodoTitleRequestDto request, @PathVariable Long id){
        todoService.editTodo(request,id);
        return ResponseEntity.status(200).body("Todo 수정 성공");
    }
}
