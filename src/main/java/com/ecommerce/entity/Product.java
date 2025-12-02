package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;
    private String productDescription;
    private double productPrice;
    private int productQuantity;


    //Relations

    @ManyToOne
    private Category category;
}
