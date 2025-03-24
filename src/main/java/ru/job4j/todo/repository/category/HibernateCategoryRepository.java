package ru.job4j.todo.repository.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepository implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<Category> findById(int id) {
        String query = "from Category where id = :fId";
        return crudRepository.optional(query, Map.of("fId", id), Category.class);
    }

    @Override
    public Collection<Category> findAll() {
        String query = "from Category";
        return crudRepository.query(query, Category.class);
    }
}
