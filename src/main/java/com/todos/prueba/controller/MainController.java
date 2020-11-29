package com.todos.prueba.controller;

import com.todos.prueba.entity.ToDo;
import com.todos.prueba.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(value = "/todos/tests")
public class MainController {

    private static final String EQUALS_SIGN = "=";
    private static final String TRUE = "true";
    private static final String STATUS = "status";
    private static final String COMPLETED = "completed";

    @Autowired
    private ToDoService toDoService;

    @RequestMapping(value = "/1", method = RequestMethod.GET)
    public Collection<ToDo> getCurrentJobs() {
        return toDoService.getByStatus(false);
    }

    @RequestMapping(value = "/1", method = RequestMethod.PATCH)
    public void stopAllJobs() {
        toDoService.stopAllJobs();
    }

    @RequestMapping(value = "/2", method = RequestMethod.POST)
    public void createUser(ToDo toDo) {
        toDoService.create(toDo);
    }

    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public Collection<ToDo> getAllByStatus(@RequestParam(required=false) String query) {
        if (StringUtils.isEmpty(query)) {
            return toDoService.getAll();
        } else {
            boolean completed;
            String[] queryParts = query.split(EQUALS_SIGN);
            if (queryParts.length > 0) {
                completed = parseCompletedByQuery(queryParts);
            } else {
                return new ArrayList<>();
            }
            return toDoService.getByStatus(completed);
        }
    }

    @RequestMapping(value = "/2/user/{userId}", method = RequestMethod.GET)
    public Collection<ToDo> getAllByUserId(@PathVariable int userId) {
        return toDoService.getByUserId(userId);
    }

    @RequestMapping(value = "/2/stats", method = RequestMethod.GET)
    public Map<Boolean, Long> getStats() {
        return toDoService.getStats();
    }

    @RequestMapping(value = "/2/titles", method = RequestMethod.GET)
    public Collection<String> getAllTitles() {
        return toDoService.getTitles();
    }

    private boolean parseCompletedByQuery(String[] queryParts) {
        boolean completed;
        completed = queryParts.length == 1 && TRUE.equalsIgnoreCase(queryParts[0]);
        completed = completed || (queryParts.length == 2
                && (STATUS.equalsIgnoreCase(queryParts[0]) && COMPLETED.equalsIgnoreCase(queryParts[1])
                || COMPLETED.equalsIgnoreCase(queryParts[0]) && TRUE.equalsIgnoreCase(queryParts[1])));
        return completed;
    }

}
