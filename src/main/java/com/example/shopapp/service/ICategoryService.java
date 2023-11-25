package com.example.shopapp.service;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(Long id);
    List<Category> getAllCategory();
    Category updateCategory(long categoryId, CategoryDTO categoryDTO);
    void deleteCategory(long id);
}
