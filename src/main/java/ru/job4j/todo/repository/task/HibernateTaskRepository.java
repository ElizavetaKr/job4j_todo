package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CrudRepository;

import java.util.*;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        String query = "DELETE Task WHERE id = :fId";
        return crudRepository.booleanQuery(query, Map.of("fId", id));
    }

    @Override
    public boolean update(Task task) {
        return crudRepository.booleanQuery(session -> session.update(task));
    }

    @Override
    public boolean updateDone(int id) {
        String query = "UPDATE Task SET done = :fDone WHERE id = :fId";
        return crudRepository.booleanQuery(query, Map.of("fDone", true, "fId", id));
    }

    @Override
    public Optional<Task> findById(int id) {
        String query = "from Task WHERE id = :fId";
        return crudRepository.optional(query, Map.of("fId", id), Task.class);
    }

    @Override
    public List<Task> findAll() {
        String query = "from Task";
        return crudRepository.query(query, Task.class);
    }

    @Override
    public List<Task> findNew() {
        String query = "from Task WHERE done = false";
        return crudRepository.query(query, Task.class);
    }

    @Override
    public List<Task> findDone() {
        String query = "from Task WHERE done = true";
        return crudRepository.query(query, Task.class);
    }
}
