package org.examportal.Services.Exam;

import org.examportal.DTOs.Exam.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto category, String user);

    CategoryDto updateCategory(CategoryDto category, String user);

    Set<CategoryDto> findAllCategories();

    Page<CategoryDto> findPaginated(Pageable pageable, String searchData);

    CategoryDto findCategoryById(Long categoryId);

    void deleteCategory(Long categoryId);
}
