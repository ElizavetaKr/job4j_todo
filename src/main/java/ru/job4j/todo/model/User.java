package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @EqualsAndHashCode.Exclude
    private String name;
    private String login;
    private String password;

    public User() {
    }
}
