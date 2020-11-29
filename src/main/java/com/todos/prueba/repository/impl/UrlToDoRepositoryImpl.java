package com.todos.prueba.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todos.prueba.entity.ToDo;
import com.todos.prueba.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Transactional
@Profile("!h2")
@Service("urlToDoRepository")
public class UrlToDoRepositoryImpl implements ToDoRepository {

    @Value("${urlDatasource}")
    private String url;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public List<ToDo> findAll() {
        try {
            System.out.println("Leyendo desde url: " + url);
            List<ToDo> toDos = Arrays.asList(new ObjectMapper().readValue(new URL(url), ToDo[].class));
            toDos.forEach(t -> Objects.requireNonNull(cacheManager.getCache("todos")).putIfAbsent(t.getId(), t));
            Map<Integer, ToDo> cache = ((ConcurrentHashMap<Integer, ToDo>)
                    cacheManager.getCache("todos").getNativeCache());
            return new ArrayList<>(cache.values());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<ToDo> findAll(Sort sort) {
        List<ToDo> sortedToDos = findAll();
        sortedToDos.sort((o1, o2) -> {
            final int[] comparation = new int[1];
            sort.forEach(t -> {
                comparation[0] =
                        t.getProperty().equalsIgnoreCase("id") ? o1.getId() - o2.getId()
                                : t.getProperty().equals("userId") ? comparation[0] + o1.getUserId() - o2.getUserId()
                                : t.getProperty().equals("title") ? comparation[0] + o1.getTitle().compareTo(o2.getTitle())
                                : comparation[0];
            });
            return comparation[0];
        });
        return sortedToDos;
    }

    @Override
    public Page<ToDo> findAll(Pageable pageable) {
        List<ToDo> ToDos = findAll();
        return new PageImpl(ToDos, pageable, ToDos.size());
    }

    @Override
    public List<ToDo> findAllById(Iterable<Integer> iterable) {
        final List<ToDo> ToDos = findAll();
        final List<ToDo> filteredList = new ArrayList<>();
        iterable.forEach(t -> filteredList
                .addAll(ToDos.stream().filter(toDo -> t.equals(toDo.getId())).collect(Collectors.toList())));
        return filteredList;
    }

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    @CacheEvict("#id")
    public void deleteById(Integer integer) {

    }

    @Override
    @CacheEvict(value = "todos", key = "#id", cacheManager = "cacheManager")
    public void delete(ToDo toDo) {

    }

    @Override
    @CacheEvict(value = "todos", key = "#p0.id", cacheManager = "cacheManager")
    public void deleteAll(Iterable<? extends ToDo> iterable) {

    }

    @Override
    public void deleteAll() {
        cacheManager.getCacheNames()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

    @Override
    @CachePut(value = "todos", key = "#p0.id", cacheManager = "cacheManager")
    public <S extends ToDo> S save(S s) {
        return s;
    }

    @Override
    public <S extends ToDo> List<S> saveAll(Iterable<S> iterable) {
        iterable.forEach(t -> Objects.requireNonNull(cacheManager.getCache("todos")).evictIfPresent(t.getId()));
        iterable.forEach(t -> Objects.requireNonNull(cacheManager.getCache("todos")).putIfAbsent(t.getId(), t));
        return (List<S>) iterable;
    }

    @Override
    public Optional<ToDo> findById(Integer integer) {
        return findAll().stream().filter(t -> t.getId() == integer).findFirst();
    }

    @Override
    public boolean existsById(Integer integer) {
        return findById(integer).isPresent();
    }

    @Override
    public void flush() {
        cacheManager.getCacheNames()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

    @Override
    public <S extends ToDo> S saveAndFlush(S s) {
        return save(s);
    }

    @Override
    @CacheEvict(value = "todos", key = "#p0.id", cacheManager = "cacheManager")
    public void deleteInBatch(Iterable<ToDo> iterable) {
        iterable.forEach(this::delete);
    }

    @Override
    public void deleteAllInBatch() {
        deleteAll();
    }

    @Override
    public ToDo getOne(Integer integer) {
        return findById(integer).isPresent() ? findById(integer).get() : null;
    }

    @Override
    public <S extends ToDo> Optional<S> findOne(Example<S> example) {
        return (Optional<S>) findById(((ToDo) example).getId());
    }

    @Override
    public <S extends ToDo> List<S> findAll(Example<S> example) {
        return (List<S>) findAll();
    }

    @Override
    public <S extends ToDo> List<S> findAll(Example<S> example, Sort sort) {
        return (List<S>) findAll(sort);
    }

    @Override
    public <S extends ToDo> Page<S> findAll(Example<S> example, Pageable pageable) {
        return (Page<S>) findAll(pageable);
    }

    @Override
    public <S extends ToDo> long count(Example<S> example) {
        return count();
    }

    @Override
    public <S extends ToDo> boolean exists(Example<S> example) {
        return existsById(((ToDo) example).getId());
    }

    @Override
    public List<ToDo> findByCompleted(boolean completed) {
        return findAll().stream().filter(t -> t.isCompleted() == completed).collect(Collectors.toList());
    }

    @Override
    public List<ToDo> findByUserId(int userId) {
        return findAll().stream().filter(t -> t.getUserId() == userId).collect(Collectors.toList());
    }
}
