package com.ecommerce.controller;

import com.ecommerce.dto.orderDTO.RequestOrder;
import com.ecommerce.entity.Order;
import com.ecommerce.service.OrderService;
import com.ecommerce.utility.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private OrderService orderService;

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<ApiResponse> checkout(@PathVariable long userId, @RequestBody RequestOrder requestOrder) {
        return orderService.checkout(userId, requestOrder);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse> finsAllOrders() {
        return orderService.getAllOrders();
    }
}
