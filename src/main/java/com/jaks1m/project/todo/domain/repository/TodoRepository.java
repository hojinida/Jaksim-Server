package com.jaks1m.project.todo.domain.repository;

import com.jaks1m.project.todo.domain.Todo;
import com.jaks1m.project.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findAllByUserAndCreateDateOrderByIdAsc(User user, LocalDate localDate);
}
