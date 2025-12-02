package com.ecommerce.dto.rabbit;

import com.ecommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private double totalAmount;

}
