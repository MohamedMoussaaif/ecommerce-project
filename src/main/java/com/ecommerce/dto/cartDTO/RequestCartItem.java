package com.ecommerce.dto.cartDTO;

import lombok.Data;

@Data
public class RequestCartItem {
    private long productId;
    private int quantity;
}
