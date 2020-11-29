package com.todos.prueba.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonSerialize
@Table(name="TODOS")
public class ToDo {
    @Column(name = "USERID")
    @NotNull(message = "UserId cannot be null")
    @Min(value = 1, message = "UserId must be at least 1 or bigger")
    private int userId;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @NotNull(message = "Id cannot be null")
    @Min(value = 1, message = "UserId must be at least 1 or bigger")
    private int id;
    @Column(name = "TITLE")
    @NotNull(message = "Title cannot be null")
    private String title;
    @Column(name = "COMPLETED")
    @NotNull(message = "Completed cannot be null")
    private boolean completed;

}
