package com.ecommerce.repository;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<List<Product>> findByCategory(Category category);
}
