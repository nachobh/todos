package com.todos.prueba.repository;

import com.todos.prueba.entity.ToDo;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Profile("h2")
public interface H2ToDoRepository extends ToDoRepository, JpaRepository<ToDo, Integer> {

    List<ToDo> findByCompleted(boolean completed);

    List<ToDo> findByUserId(int userId);

}
