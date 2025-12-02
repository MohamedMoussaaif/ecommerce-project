package com.ecommerce.dto.rabbit;

import com.ecommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusChangedEvent {
    private Long orderId;
    private OrderStatus oldStatus;
    private OrderStatus newStatus;
    private LocalDateTime changedAt;
}
