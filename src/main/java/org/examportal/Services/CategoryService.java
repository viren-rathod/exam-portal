package org.examportal.Services;

import org.examportal.DTOs.CategoryDto;

import java.util.Set;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto category, String user);

    CategoryDto updateCategory(CategoryDto category, String user);

    Set<CategoryDto> getCategories();

    CategoryDto getCategory(Long categoryId);

    void deleteCategory(Long categoryId);
}
