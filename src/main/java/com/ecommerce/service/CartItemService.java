package com.ecommerce.service;

import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.exception.OrderItemNotFound;
import com.ecommerce.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
class CartItemService {

    private final CartItemRepository cartItemRepository;

    public void addCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    public void updateOrderItem(long itemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(() -> new OrderItemNotFound("Cart item not found"));
        cartItem.setQuantity(quantity);
        double price = cartItem.getProduct().getProductPrice();
        cartItem.setPriceAtPurchase(price * quantity);
        cartItemRepository.save(cartItem);

    }

    public void deleteCartItem(long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(() -> new OrderItemNotFound("Cart item not found"));
        cartItemRepository.delete(cartItem);
    }

}
