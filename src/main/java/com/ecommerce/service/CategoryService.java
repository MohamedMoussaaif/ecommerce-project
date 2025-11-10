package com.ecommerce.service;

import com.ecommerce.entity.Category;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {

        return categoryRepository.save(category);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
