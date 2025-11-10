package com.ecommerce.controller;


import com.ecommerce.dto.productDTO.RequestProduct;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.productById(id);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.allProducts();
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public Product addProduct(@RequestBody RequestProduct product) {
        return productService.createProduct(product);
    }

    /*@PutMapping("/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) {

    }*/

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}
