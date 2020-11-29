package com.todos.prueba.service;

import com.todos.prueba.entity.ToDo;

import java.util.Collection;
import java.util.Map;

public interface ToDoService {

    ToDo create(ToDo item);
    Collection<ToDo> getAll();
    Collection<ToDo> getByStatus(boolean isCompleted);
    Collection<ToDo> getByUserId(int userId);

    /**
     * ○ Devuelve un mapa del tipo
     * ● false: <número de elementos sin completar>
     * ● true: <número de elementos completados>
     */
    Map<Boolean, Long> getStats();

    /**
     * Devuelve una lista con los títulos de todos los elementos, ordenados de menor a mayor
     *     según su longitud
     * @return Collection<String>
     */
    Collection<String> getTitles();

    void stopAllJobs();

}
