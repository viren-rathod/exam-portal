package org.examportal.Controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.examportal.DTOs.BaseResponseDto;
import org.examportal.DTOs.CategoryDto;
import org.examportal.DTOs.Response;
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
    public ResponseEntity<BaseResponseDto<CategoryDto>> addCategory(@Valid @RequestBody CategoryDto categoryDto, Principal principal) {
        CategoryDto savedCategory = categoryService.addCategory(categoryDto, principal.getName());
        Response<CategoryDto> response = new Response<>(savedCategory);
        response.setResponseCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //get category
    @GetMapping("/{categoryId}")
    public ResponseEntity<BaseResponseDto<CategoryDto>> getCategory(@PathVariable("categoryId") Long categoryId) {
        CategoryDto categoryDto = categoryService.findCategoryById(categoryId);
        Response<CategoryDto> response = new Response<>(categoryDto);
        response.setResponseCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //get all categories
    @GetMapping("/")
    public ResponseEntity<BaseResponseDto<Set<CategoryDto>>> getCategories() {
        Set<CategoryDto> allCategories = categoryService.findAllCategories();
        Response<Set<CategoryDto>> response = new Response<>(allCategories);
        response.setResponseCode(allCategories.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        if (allCategories.isEmpty()) response.setMessage("No Content!");
        return new ResponseEntity<>(response, allCategories.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    //update category
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<BaseResponseDto<CategoryDto>> updateCategory(@Valid @RequestBody CategoryDto category, Principal principal) {
        CategoryDto categoryDto = categoryService.updateCategory(category, principal.getName());
        Response<CategoryDto> response = new Response<>(categoryDto);
        response.setResponseCode(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    //delete category
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<BaseResponseDto<String>> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        Response<String> response = new Response<>("Category deleted successfully!");
        response.setResponseCode(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}