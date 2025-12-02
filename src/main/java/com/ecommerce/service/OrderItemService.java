package com.ecommerce.service;

import com.ecommerce.entity.OrderItem;
import com.ecommerce.exception.OrderItemNotFound;
import com.ecommerce.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public void addOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void updateOrderItem(long itemId, int quantity) {
        OrderItem orderItem = orderItemRepository.findById(itemId).orElseThrow(() -> new OrderItemNotFound("Items not found"));
        orderItem.setQuantity(quantity);
        double price = orderItem.getProduct().getProductPrice();
        orderItem.setPriceAtPurchase(price * quantity);
        orderItemRepository.save(orderItem);

    }


}
