package com.todos.prueba.repository;

import com.todos.prueba.entity.ToDo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ToDoRepository {

    List<ToDo> findAll();

    List<ToDo> findAll(Sort sort);

    Page<ToDo> findAll(Pageable pageable);

    List<ToDo> findAllById(Iterable<Integer> iterable);

    long count();

    void deleteById(Integer integer);

    void delete(ToDo toDo);

    void deleteAll(Iterable<? extends ToDo> iterable);

    void deleteAll();

    <S extends ToDo> S save(S s);

    <S extends ToDo> List<S> saveAll(Iterable<S> iterable);

    Optional<ToDo> findById(Integer integer);

    boolean existsById(Integer integer);

    void flush();

    <S extends ToDo> S saveAndFlush(S s);

    void deleteInBatch(Iterable<ToDo> iterable);

    void deleteAllInBatch();

    ToDo getOne(Integer integer);

    <S extends ToDo> Optional<S> findOne(Example<S> example);

    <S extends ToDo> List<S> findAll(Example<S> example);

    <S extends ToDo> List<S> findAll(Example<S> example, Sort sort);

    <S extends ToDo> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends ToDo> long count(Example<S> example);

    <S extends ToDo> boolean exists(Example<S> example);

    List<ToDo> findByCompleted(boolean completed);

    List<ToDo> findByUserId(int userId);
}
