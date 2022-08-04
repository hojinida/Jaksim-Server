package com.jaks1m.project.repository.func;

import com.jaks1m.project.domain.entity.todo.Todo;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.dto.todo.TodoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findAllByUserAndCreateDateOrderByIdDesc(User user, LocalDate localDate);
}
