package com.ecommerce.mapper.rabbit;

import com.ecommerce.dto.rabbit.OrderCreatedEvent;
import com.ecommerce.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventMapper {

    public OrderCreatedEvent orderToCreatedEvent(Order order) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrderId(order.getId());
        orderCreatedEvent.setOrderStatus(order.getOrderStatus());
        orderCreatedEvent.setOrderDate(order.getOrderDate());
        orderCreatedEvent.setTotalAmount(order.getTotalAmount());   

        return orderCreatedEvent;
    }
}
