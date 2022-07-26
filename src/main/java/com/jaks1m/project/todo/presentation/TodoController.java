package com.jaks1m.project.todo.presentation;

import com.jaks1m.project.todo.presentation.dto.TodoEditCompletedRequest;
import com.jaks1m.project.todo.presentation.dto.TodoEditTitleRequest;
import com.jaks1m.project.todo.presentation.dto.TodoCreateRequest;
import com.jaks1m.project.todo.application.dto.TodoResponse;
import com.jaks1m.project.todo.application.TodoService;
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
    public ResponseEntity<String> addTodo(@RequestBody @Validated TodoCreateRequest request){
        todoService.addTodo(request);
        return ResponseEntity.status(200).body("Todo 등록 성공");
    }

    @GetMapping
    @ApiOperation(value = "todo 조회")
    public ResponseEntity<List<TodoResponse>> getTodo(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate){
        return ResponseEntity.status(200)
                .body(todoService.getTodo(localDate));
    }

    @PatchMapping("/{id}/title")
    @ApiOperation(value = "todo 내용 수정")
    public ResponseEntity<String> editTodoTitle(@RequestBody @Validated TodoEditTitleRequest request, @PathVariable Long id){
        todoService.editTodoTitle(request,id);
        return ResponseEntity.status(200).body("Todo 내용 수정 성공");
    }

    @PatchMapping("/{id}/completed")
    @ApiOperation(value = "todo 체크박스 수정")
    public ResponseEntity<String> editTodoCompleted(@RequestBody @Validated TodoEditCompletedRequest request, @PathVariable Long id){
        todoService.editTodoCompleted(request,id);
        return ResponseEntity.status(200).body("Todo 체크박스 수정 성공");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "todo 삭제")
    public ResponseEntity<String> deleteTodoCompleted(@PathVariable Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.status(200).body("Todo 삭제 성공");
    }
}
