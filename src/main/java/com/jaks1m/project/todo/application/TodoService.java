package com.jaks1m.project.todo.application;


import com.jaks1m.project.auth.support.SecurityUtil;
import com.jaks1m.project.todo.domain.Todo;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.common.exception.ErrorCode;
import com.jaks1m.project.common.exception.CustomException;
import com.jaks1m.project.todo.presentation.dto.TodoEditCompletedRequest;
import com.jaks1m.project.todo.presentation.dto.TodoEditTitleRequest;
import com.jaks1m.project.todo.presentation.dto.TodoCreateRequest;
import com.jaks1m.project.todo.application.dto.TodoResponse;
import com.jaks1m.project.todo.domain.repository.TodoRepository;
import com.jaks1m.project.user.domain.repository.UserRepository;
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
    public void addTodo(TodoCreateRequest request){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        todoRepository.save(Todo.builder()
                .createDate(LocalDate.now())
                .title(request.getTitle())
                .completed(request.getCompleted())
                .user(user)
                .build());
    }

    public List<TodoResponse> getTodo(LocalDate localDate){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Todo> todoList= todoRepository.findAllByUserAndCreateDateOrderByIdAsc(user, localDate);
        List<TodoResponse> result=new ArrayList<>();
        todoList.forEach(todo -> result.add(TodoResponse.builder()
                .id(todo.getId()).title(todo.getTitle()).completed(todo.getCompleted()).build()));
        return result;
    }
    @Transactional
    public void editTodoTitle(TodoEditTitleRequest request, Long id){
        Todo todo =checkUnauthorizedAccess(id);
        todo.updateTitle(request.getTitle());
    }

    @Transactional
    public void editTodoCompleted(TodoEditCompletedRequest request, Long id){
        Todo todo=checkUnauthorizedAccess(id);
        todo.updateCompleted(request.getCompleted());
    }
    @Transactional
    public void deleteTodo(Long id){
        Todo todo=checkUnauthorizedAccess(id);
        todo.deleteUser();
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
