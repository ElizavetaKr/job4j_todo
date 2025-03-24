package ru.job4j.todo.service.category;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(int id);

    Collection<Category> findAll();
}
