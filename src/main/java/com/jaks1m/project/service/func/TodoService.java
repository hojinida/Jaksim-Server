package com.jaks1m.project.service.func;


import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.entity.todo.Todo;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.dto.todo.EditTodoCompletedRequestDto;
import com.jaks1m.project.dto.todo.EditTodoTitleRequestDto;
import com.jaks1m.project.dto.todo.AddTodoRequestDto;
import com.jaks1m.project.dto.todo.GetTodoResponseDto;
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
    public void addTodo(AddTodoRequestDto request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        todoRepository.save(Todo.builder()
                .createDate(LocalDate.now())
                .title(request.getTitle())
                .completed(request.getCompleted())
                .user(user)
                .build());
    }

    public List<GetTodoResponseDto> getTodo(LocalDate localDate){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Todo> todoList= todoRepository.findAllByUserAndCreateDateOrderByIdAsc(user, localDate);
        List<GetTodoResponseDto> result=new ArrayList<>();
        todoList.forEach(todo -> result.add(GetTodoResponseDto.builder()
                .id(todo.getId()).title(todo.getTitle()).completed(todo.getCompleted()).build()));
        return result;
    }
    @Transactional
    public void editTodoTitle(EditTodoTitleRequestDto request, Long id){
        Todo todo =checkUnauthorizedAccess(id);
        todo.updateTitle(request.getTitle());
    }

    @Transactional
    public void editTodoCompleted(EditTodoCompletedRequestDto request, Long id){
        Todo todo=checkUnauthorizedAccess(id);
        todo.updateCompleted(request.getCompleted());
    }
    @Transactional
    public void deleteTodo(Long id){
        Todo todo=checkUnauthorizedAccess(id);
        todoRepository.delete(todo);
    }

    private Todo checkUnauthorizedAccess(Long id) {
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_TODO);
        }
        if(user!=todo.get().getUser()){//다른 회원이 수정 시도했을 떄
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
        return todo.get();
    }
}
