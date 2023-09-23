package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.examportal.DTOs.CategoryDto;
import org.examportal.Services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //add category
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto, Principal principal) {
        CategoryDto savedCategory = categoryService.addCategory(categoryDto, principal.getName());
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    //get category
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Long categoryId) {
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    //get all categories
    @GetMapping("/")
    public ResponseEntity<Set<CategoryDto>> getCategories() {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    //update category
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto category, Principal principal) {
        return ResponseEntity.ok(categoryService.updateCategory(category, principal.getName()));
    }

    //delete category
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully!");
    }
}