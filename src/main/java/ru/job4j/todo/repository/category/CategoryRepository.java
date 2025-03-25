package ru.job4j.todo.repository.category;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository {

    Collection<Category> findByList(List<Integer> categoriesId);

    Collection<Category> findAll();
}
