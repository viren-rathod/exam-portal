package org.examportal.Services.Exam;

import org.examportal.DTOs.Exam.CategoryDto;

import java.util.Set;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto category, String user);

    CategoryDto updateCategory(CategoryDto category, String user);

    Set<CategoryDto> findAllCategories();

    CategoryDto findCategoryById(Long categoryId);

    void deleteCategory(Long categoryId);
}
