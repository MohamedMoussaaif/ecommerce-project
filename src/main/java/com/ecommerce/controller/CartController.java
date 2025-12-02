package com.ecommerce.controller;

import com.ecommerce.dto.cartDTO.RequestCartItem;
import com.ecommerce.dto.cartDTO.RequestOrderItem;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.service.CartService;
import com.ecommerce.utility.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @PostMapping("cart/{userId}")
    public ResponseEntity<ApiResponse> addToCart(@PathVariable long userId, @RequestBody RequestCartItem orderItem) {

        return cartService.addItemToCart(userId, orderItem);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable long userId) {
        return cartService.getCart(userId);
    }

    @DeleteMapping("cart/{userId}/{itemId}")
    public ResponseEntity<ApiResponse> deleteFromCart(@PathVariable long userId, @PathVariable long itemId) {
        return cartService.removeItemFromCart(userId, itemId);
    }

    @DeleteMapping("/cart/{userId}")
    public ResponseEntity<ApiResponse> deleteAllItemsFromCart(@PathVariable long userId) {
        return cartService.removeAllItemsFromCart(userId);
    }

    @PutMapping("/cart/{userId}/{itemId}")
    public ResponseEntity<ApiResponse> updateCart(@PathVariable long userId, @PathVariable long itemId, @RequestBody int quantity) {
        return cartService.updateItem(userId, itemId, quantity);
    }
}
