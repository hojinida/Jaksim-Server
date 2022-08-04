package com.jaks1m.project.service.func;


import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.entity.todo.Todo;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.dto.todo.request.TodoAddRequest;
import com.jaks1m.project.repository.func.TodoRepository;
import com.jaks1m.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    @Transactional
    public void addTodo(TodoAddRequest request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        todoRepository.save(Todo.builder()
                .createDate(LocalDate.now())
                .title(request.getTitle())
                .completed(request.getCompleted())
                .user(user)
                .build());
    }
}
