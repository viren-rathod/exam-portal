package org.examportal.Controllers.Exam;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.examportal.Constants.Exam.CategoryMessages;
import org.examportal.Constants.UserMessages;
import org.examportal.DTOs.BaseResponseDto;
import org.examportal.DTOs.Exam.CategoryDto;
import org.examportal.DTOs.Response;
import org.examportal.Services.Exam.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
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
        log.info(String.format("addCategory() - start %s", categoryDto));
        CategoryDto savedCategory = categoryService.addCategory(categoryDto, principal.getName());
        Response<CategoryDto> response = new Response<>(savedCategory);
        response.setResponseCode(HttpStatus.CREATED.value());
        response.setMessage(CategoryMessages.CATEGORY_CREATED);
        response.setToast(true);
        log.info(String.format("addCategory() - end %s", savedCategory));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //get category
    @GetMapping("/{categoryId}")
    public ResponseEntity<BaseResponseDto<CategoryDto>> getCategory(@PathVariable("categoryId") Long categoryId) {
        log.info(String.format("getCategory() - start %d", categoryId));
        CategoryDto categoryDto = categoryService.findCategoryById(categoryId);
        Response<CategoryDto> response = new Response<>(categoryDto);
        response.setResponseCode(HttpStatus.OK.value());
        response.setMessage(CategoryMessages.CATEGORY_FETCHED);
        log.info(String.format("getCategory() - end %s", categoryDto));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //get all categories
    @GetMapping("/")
    public ResponseEntity<BaseResponseDto<Set<CategoryDto>>> getCategories() {
        log.info("getCategories() - start");
        Set<CategoryDto> allCategories = categoryService.findAllCategories();
        Response<Set<CategoryDto>> response = new Response<>(allCategories, allCategories.size(), allCategories.isEmpty());
        response.setResponseCode(allCategories.isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        response.setMessage(allCategories.isEmpty() ? UserMessages.NO_CONTENT : CategoryMessages.CATEGORY_FETCHED);
        log.info(String.format("getCategories() - end %s ", allCategories));
        return new ResponseEntity<>(response, allCategories.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    //get categories with Pagination
    @GetMapping("/paginated")
    public ResponseEntity<Response<Page<CategoryDto>>> getPaginatedCategories(@RequestParam(required = false, defaultValue = "0") int page,
                                                                              @RequestParam(required = false, defaultValue = "10") int size,
                                                                              @RequestParam(required = false, defaultValue = "asc") String sortOrder,
                                                                              @RequestParam(required = false, defaultValue = "id") String sortField,
                                                                              @RequestParam(required = false) String searchData) {
        log.info(String.format("getPaginatedCategories() - start %d %d %s %s %s", page, size, sortOrder, sortField, searchData));
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CategoryDto> paginated = categoryService.findPaginated(pageable, searchData);
        Response<Page<CategoryDto>> response = new Response<>(paginated, paginated.getContent().size(), paginated.getContent().isEmpty());
        response.setResponseCode(response.getData().isEmpty() ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value());
        response.setMessage(response.getData().isEmpty() ? UserMessages.NO_CONTENT : CategoryMessages.CATEGORY_FETCHED);
        log.info(String.format("getPaginatedCategories() - end %s", paginated.getContent()));
        return new ResponseEntity<>(response, response.getData().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    //update category
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<BaseResponseDto<CategoryDto>> updateCategory(@Valid @RequestBody CategoryDto category, Principal principal) {
        log.info(String.format("updateCategory() - start %s", category));
        CategoryDto categoryDto = categoryService.updateCategory(category, principal.getName());
        Response<CategoryDto> response = new Response<>(categoryDto);
        response.setResponseCode(HttpStatus.OK.value());
        response.setMessage(CategoryMessages.CATEGORY_UPDATED);
        response.setToast(true);
        log.info(String.format("updateCategory() - end %s", categoryDto));
        return ResponseEntity.ok(response);
    }

    //delete category
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<BaseResponseDto<String>> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        log.info(String.format("deleteCategory() - start %d", categoryId));
        categoryService.deleteCategory(categoryId);
        Response<String> response = new Response<>(CategoryMessages.CATEGORY_DELETED);
        response.setResponseCode(HttpStatus.OK.value());
        response.setMessage(CategoryMessages.CATEGORY_DELETED);
        response.setToast(true);
        log.info("deleteCategory() - end");
        return ResponseEntity.ok(response);
    }
}