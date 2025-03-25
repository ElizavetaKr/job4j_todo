package ru.job4j.todo.repository.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CrudRepository;

import java.util.*;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public List<Category> findByList(List<Integer> categoriesId) {
        String query = "from Category where id IN :fCategoriesId";
        return crudRepository.query(query, Map.of("fCategoriesId", categoriesId), Category.class);
    }

    @Override
    public Collection<Category> findAll() {
        String query = "from Category";
        return crudRepository.query(query, Category.class);
    }
}
