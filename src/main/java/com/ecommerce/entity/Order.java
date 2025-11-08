package com.ecommerce.entity;

import com.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalAmount;

    //Shipping details
    private String shippingAddress;
    private String country;

    private OrderStatus orderStatus;

    private LocalDateTime orderDate = LocalDateTime.now();
    private LocalDateTime deliveredDate;

    //Relations

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
