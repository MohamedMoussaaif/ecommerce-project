package com.ecommerce.controller;

import com.ecommerce.dto.orderDTO.OrderStatusRequest;
import com.ecommerce.dto.orderDTO.RequestOrder;
import com.ecommerce.entity.Order;
import com.ecommerce.service.OrderService;
import com.ecommerce.utility.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse> findAllOrders() {
        return orderService.getAllOrders();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable long orderId, @RequestBody OrderStatusRequest orderStatusRequest) {
        return orderService.updateOrderStatus(orderId, orderStatusRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable long orderId) {
        return orderService.removeOrder(orderId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable long orderId) {
        return orderService.orderById(orderId);
    }
}
