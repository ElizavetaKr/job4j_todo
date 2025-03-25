package ru.job4j.todo.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.category.CategoryRepository;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Collection<Category> findByList(List<Integer> categoriesId) {
        return categoryRepository.findByList(categoriesId);
    }

    @Override
    public Collection<Category> findAll() {
        return categoryRepository.findAll();
    }
}
