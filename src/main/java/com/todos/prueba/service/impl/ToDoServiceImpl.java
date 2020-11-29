package com.todos.prueba.service.impl;

import com.todos.prueba.entity.ToDo;
import com.todos.prueba.repository.ToDoRepository;
import com.todos.prueba.service.ToDoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ToDoServiceImpl implements ToDoService {

    @Getter
    @Setter
    private ToDoRepository toDoRepository;

    @Autowired(required = false)
    private ToDoRepository urlToDoRepository;

    @Autowired(required = false)
    private ToDoRepository h2ToDoRepository;

    @PostConstruct
    public void init() {
        this.setToDoRepository(h2ToDoRepository != null ? h2ToDoRepository : toDoRepository);
    }

    @Override
    public ToDo create(ToDo item) {
        return toDoRepository.save(item);
    }

    @Override
    public Collection<ToDo> getAll() {
        return toDoRepository.findAll();
    }

    @Override
    public Collection<ToDo> getByStatus(boolean isCompleted) {
        return toDoRepository.findByCompleted(isCompleted);
    }

    @Override
    public Collection<ToDo> getByUserId(int userId) {
        return toDoRepository.findByUserId(userId);
    }

    @Override
    public Map<Boolean, Long> getStats() {
        Map<Boolean, Long> statsMap = new HashMap<>();
        statsMap.put(true, toDoRepository.findAll().stream().filter(ToDo::isCompleted).count());
        statsMap.put(false, toDoRepository.count() - statsMap.get(true));
        return statsMap;
    }

    @Override
    public Collection<String> getTitles() {
        return toDoRepository.findAll().stream().map(ToDo::getTitle).collect(Collectors.toList());
    }

    @Override
    public void stopAllJobs() {
        Iterable<ToDo> s = getByStatus(false);
        s.forEach(t -> t.setCompleted(true));
        toDoRepository.saveAll(s);
    }
}
