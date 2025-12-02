package com.ecommerce.controller;


import com.ecommerce.dto.productDTO.RequestProduct;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import com.ecommerce.utility.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable long id) {
        return productService.productById(id);
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse> getAllProducts() {
        return productService.allProducts();
    }

    @GetMapping("/products/category/{category}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) {
        return productService.productsByCategory(category);
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody RequestProduct product) {
        return productService.createProduct(product);
    }

    /*@PutMapping("/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) {

    }*/

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}
