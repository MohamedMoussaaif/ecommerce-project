package com.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double shippingCost = 5.99;
    private double subtotal;
    private double totalPrice;



    //Relations

    @OneToMany(cascade = CascadeType.ALL)
    @Column(table = "cart_cartItems")
    private List<CartItem> items = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

}
