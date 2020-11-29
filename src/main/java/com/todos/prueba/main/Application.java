package com.todos.prueba.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println("Application running in Thread: " + Thread.currentThread().getName());
        SpringApplication.run(Application.class, args);
    }
}
