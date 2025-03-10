package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    @EqualsAndHashCode.Exclude
    private LocalDateTime created;
    private boolean done;

    public Task() {
        created = LocalDateTime.now();
        done = false;
    }
}
