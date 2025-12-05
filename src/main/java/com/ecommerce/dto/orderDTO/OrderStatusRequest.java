package com.ecommerce.dto.orderDTO;

import com.ecommerce.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusRequest {
    private String orderStatus;
}
