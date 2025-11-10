package com.ecommerce.service;

import com.ecommerce.dto.productDTO.RequestProduct;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductMapper productMapper;

    public Product productById(long id) {
        return productRepository.findById(id);
    }

    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(RequestProduct product) {

        Product targetProduct = productMapper.toProduct(product);

        Category cat = categoryService.findByName(product.getCategoryName());
        targetProduct.setCategory(cat);
        return productRepository.save(targetProduct);
    }

    public Product deleteProduct(long id) {
        Product targetProduct = productRepository.findById(id);
        productRepository.delete(targetProduct);
        return targetProduct;
    }

}
