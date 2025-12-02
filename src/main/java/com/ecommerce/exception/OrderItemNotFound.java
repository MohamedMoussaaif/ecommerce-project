package com.ecommerce.exception;

public class OrderItemNotFound extends RuntimeException{
    public OrderItemNotFound(String message) {
        super(message);
    }
}
