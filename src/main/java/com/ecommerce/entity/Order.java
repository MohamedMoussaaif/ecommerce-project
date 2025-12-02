package com.ecommerce.entity;

import com.ecommerce.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private double shippingCost = 5.99;
    private double subtotal;

    //Shipping details
    private String shippingAddress;
    private String country;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    private LocalDateTime orderDate = LocalDateTime.now();
    private LocalDateTime deliveredDate = null;



    //Relations

    @OneToMany(cascade = CascadeType.ALL)
    @Column(table = "order_orderItems")
    private List<OrderItem> items;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
}
