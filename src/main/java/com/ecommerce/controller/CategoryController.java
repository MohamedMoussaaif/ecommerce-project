package com.ecommerce.controller;

import com.ecommerce.dto.categoryDTO.CategoryRequest;
import com.ecommerce.entity.Category;
import com.ecommerce.service.CategoryService;
import com.ecommerce.utility.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getCategories() {
        return categoryService.findAllCategories();
    }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addCategorie(@RequestBody CategoryRequest category) {
        return categoryService.addCategory(category);
    }

    @DeleteMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategorie(@PathVariable long categoryId) {
        return categoryService.removeCategory(categoryId);
    }

    @GetMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getCategorieById(@PathVariable long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }

    @PatchMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateCategorieName(@PathVariable long categoryId, @RequestBody CategoryRequest category) {
        return categoryService.changeCategoryName(categoryId, category);
    }
}
