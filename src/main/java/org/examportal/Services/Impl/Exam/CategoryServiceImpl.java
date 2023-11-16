package org.examportal.Services.Impl.Exam;

import lombok.extern.slf4j.Slf4j;
import org.examportal.DTOs.Exam.CategoryDto;
import org.examportal.Exceptions.ResourceNotFoundException;
import org.examportal.Models.Exam.Category;
import org.examportal.Repositories.Exam.CategoryRepository;
import org.examportal.Services.Exam.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
        log.info(String.format("addCategory - start %s", categoryDto));
        Category category = modelMapper.map(categoryDto, Category.class);
        category.update(user);
        Category savedCategory = categoryRepository.save(category);
        log.info(String.format("categoryDto - end %s", savedCategory));
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String user) {
        log.info(String.format("updateCategory - start %s", categoryDto));
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.findById(category.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", category.getId()));
        category.update(user);
        Category savedCategory = categoryRepository.save(category);
        log.info(String.format("updateCategory - end %s", categoryDto));
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public Set<CategoryDto> findAllCategories() {
        log.info("findAllCategories - start");
        Set<Category> categories = new LinkedHashSet<>(categoryRepository.findAll());
        log.info(String.format("findAllCategories - end %s", categories));
        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toSet());
    }

    @Override
    public Page<CategoryDto> findPaginated(Pageable pageable, String searchData) {
        log.info(String.format("findPaginated - start %s %s", pageable, searchData));
        Page<Category> page = categoryRepository.findAllWithFilters(searchData, pageable);
        log.info(String.format("findPaginated - end %s", page));
        return page.map(category -> modelMapper.map(category, CategoryDto.class));
    }

    @Override
    public CategoryDto findCategoryById(Long categoryId) {
        Category category = null;
        log.info(String.format("findCategoryById - start %d", categoryId));
        category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        log.info(String.format("findCategoryById - end %s", category));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        log.info("deleteCategory - start");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);
        log.info("deleteCategory - end");
    }
}
