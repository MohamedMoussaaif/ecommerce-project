package com.ecommerce.service;

import com.ecommerce.dto.categoryDTO.CategoryRequest;
import com.ecommerce.entity.Category;
import com.ecommerce.exception.CategoryNotFoundException;
import com.ecommerce.mapper.CategoryMapper;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.utility.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ResponseEntity<ApiResponse> addCategory(CategoryRequest category) {

        Category cat = categoryMapper.toCategory(category);
        categoryRepository.save(cat);
        return new ResponseEntity<ApiResponse>(new ApiResponse(cat, "Category added successfully", HttpStatus.OK.value()),  HttpStatus.OK);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public ResponseEntity<ApiResponse> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(new ApiResponse(categories, "Categories table not empty", HttpStatus.OK.value()));
    }

    public ResponseEntity<ApiResponse> removeCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        categoryRepository.delete(category);
        return new ResponseEntity<>(new ApiResponse(null, "Category removed successfully", HttpStatus.OK.value()),  HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> findCategoryById(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return new ResponseEntity<>(new ApiResponse(category, "Category found successfully", HttpStatus.OK.value()),  HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> changeCategoryName(long categoryId, CategoryRequest category) {
        Category cat = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        category.setName(category.getName());
        categoryRepository.save(cat);

        return new ResponseEntity<>(new ApiResponse(category, "Category changed successfully", HttpStatus.OK.value()),  HttpStatus.OK);
    }
}
