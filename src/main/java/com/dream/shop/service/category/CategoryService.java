package com.dream.shop.service.category;

import com.dream.shop.exceptions.CategoryNotFoundException;
import com.dream.shop.model.Category;
import com.dream.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found exeption"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
//                .orElseThrow(()-> new CategoryNotFoundException("Category not found exeption"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c-> !categoryRepository.existsByName(c.getName())).map(categoryRepository::save).orElseThrow(()-> new IllegalArgumentException("Category already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory-> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()-> new CategoryNotFoundException("Category not found exeption"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, ()-> {throw new CategoryNotFoundException("Category not found exeption");});
    }
}
