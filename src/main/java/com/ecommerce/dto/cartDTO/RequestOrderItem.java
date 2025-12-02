package com.ecommerce.dto.cartDTO;

import lombok.Data;

@Data
public class RequestOrderItem {

    private long productId;
    private int quantity;
}
