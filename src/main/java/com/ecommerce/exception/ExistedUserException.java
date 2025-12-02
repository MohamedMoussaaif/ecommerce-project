package com.ecommerce.exception;

public class ExistedUserException extends RuntimeException {

    public ExistedUserException(String message) {
        super(message);
    }
}
