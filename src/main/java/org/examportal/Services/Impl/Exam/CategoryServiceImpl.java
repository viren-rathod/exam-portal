package org.examportal.Services.Impl.Exam;

import org.examportal.DTOs.Exam.CategoryDto;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Models.Exam.Category;
import org.examportal.Repositories.Exam.CategoryRepository;
import org.examportal.Services.Exam.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto, String user) {
        Category category = modelMapper.map(categoryDto, Category.class);
        category.update(user);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String user) {
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.findById(category.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", category.getId()));
        category.update(user);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public Set<CategoryDto> findAllCategories() {
        Set<Category> categories = new LinkedHashSet<>(categoryRepository.findAll());
        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toSet());
    }

    @Override
    public CategoryDto findCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);
    }
}
