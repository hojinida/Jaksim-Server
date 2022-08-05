package com.jaks1m.project.service.func;


import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.entity.todo.Todo;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.dto.todo.EditTodoTitleRequestDto;
import com.jaks1m.project.dto.todo.TodoDto;
import com.jaks1m.project.repository.func.TodoRepository;
import com.jaks1m.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    @Transactional
    public void addTodo(TodoDto request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        todoRepository.save(Todo.builder()
                .createDate(LocalDate.now())
                .title(request.getTitle())
                .completed(request.getCompleted())
                .user(user)
                .build());
    }

    public List<Todo> getTodo(LocalDate localDate){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        return todoRepository.findAllByUserAndCreateDateOrderByIdAsc(user, localDate);
    }
    @Transactional
    public void editTodo(EditTodoTitleRequestDto request, Long id){
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_TODO);
        }
        todo.get().updateTitle(request.getTitle());
    }
}
