package ru.job4j.todo.repository.user;

import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.TimeZone;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);

    Collection<TimeZone> findAllZones();

}
