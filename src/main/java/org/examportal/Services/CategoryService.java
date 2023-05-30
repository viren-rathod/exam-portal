package org.examportal.Services;

import org.examportal.DTOs.CategoryDto;

import java.util.Set;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto category);

    CategoryDto updateCategory(CategoryDto category);

    Set<CategoryDto> getCategories();

    CategoryDto getCategory(Long categoryId);

    void deleteCategory(Long categoryId);
}
