package com.todos.prueba.main;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SchedulingTask {

    @Scheduled(fixedDelayString = "${scheduling.task.main.fixedDelay:1000}",
            initialDelayString = "${scheduling.task.main.initialDelay:5000}")
    public void main() {
        System.out.println("Test job executed at " + new Date()
                + " on Thread: " + Thread.currentThread().getName());
 }

}
