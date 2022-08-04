package com.jaks1m.project.repository.func;

import com.jaks1m.project.domain.entity.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {

}
