package com.jaks1m.project.controller.todo;

import com.jaks1m.project.dto.todo.request.TodoAddRequest;
import com.jaks1m.project.service.func.TodoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoController {
    private final TodoService todoService;
    @PostMapping
    @ApiOperation(value = "todo 등록")
    public ResponseEntity<String> addTodo(@RequestBody @Validated TodoAddRequest request){
        todoService.addTodo(request);
        return ResponseEntity.status(200).body("Todo 등록 성공");
    }
}
