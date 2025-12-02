package com.ecommerce.service;

import com.ecommerce.dto.productDTO.RequestProduct;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.utility.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductMapper productMapper;

    public ResponseEntity<ApiResponse> productById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with Id : " + id));
        return new ResponseEntity<ApiResponse>(new ApiResponse(product,"Product founded", HttpStatus.FOUND.value()),  HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> allProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(new ArrayList<>(),"No product found",HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(products,"Products list not empty",HttpStatus.FOUND.value()),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> createProduct(RequestProduct product) {

        Product targetProduct = productMapper.toProduct(product);

        Category cat = categoryService.findByName(product.getCategoryName());
        if(cat==null){
            throw new ProductNotFoundException("Category not found");
        }
        targetProduct.setCategory(cat);
        Product prod = productRepository.save(targetProduct);
        return new ResponseEntity<ApiResponse>(new ApiResponse(prod,"Product created",HttpStatus.CREATED.value()),HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse> deleteProduct(long id) {
        Product targetProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with Id : " + id));
        productRepository.delete(targetProduct);
        return new ResponseEntity<ApiResponse>(new ApiResponse(targetProduct,"Product deletes",HttpStatus.OK.value()),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> productsByCategory(String category) {
        Category cat = categoryService.findByName(category);
        List<Product> products = productRepository.findByCategory(cat).orElseThrow(() -> new ProductNotFoundException("Products not found with Category : " + category));
        return new ResponseEntity<ApiResponse>(new ApiResponse(products,"Products list not empty",HttpStatus.FOUND.value()),HttpStatus.OK);

    }
}
