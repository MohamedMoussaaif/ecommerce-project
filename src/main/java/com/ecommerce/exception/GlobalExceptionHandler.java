package com.ecommerce.exception;

import com.ecommerce.utility.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setMessage(ex.getMessage());
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setTimestamp(new Date());

        ApiResponse apiResponse = new ApiResponse(errorObject,ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);

    }

    @ExceptionHandler(ExistedUserException.class)
    public ResponseEntity<ApiResponse> handleExistedUserException(ExistedUserException ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setMessage(ex.getMessage());
        errorObject.setStatusCode(HttpStatus.CONFLICT.value());
        errorObject.setTimestamp(new Date());

        ApiResponse apiResponse = new ApiResponse(errorObject,ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setMessage(ex.getMessage());
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setTimestamp(new Date());

        ApiResponse apiResponse = new ApiResponse(errorObject, ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(OrderItemNotFound.class)
    public ResponseEntity<ApiResponse> handleOrderItemNotFoundException(OrderItemNotFound ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setMessage(ex.getMessage());
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setTimestamp(new Date());

        ApiResponse apiResponse = new ApiResponse(errorObject, ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(LoginErrorException.class)
    public ResponseEntity<ApiResponse> handleLoginErrorException(LoginErrorException ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setMessage(ex.getMessage());
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setTimestamp(new Date());

        ApiResponse apiResponse = new ApiResponse(errorObject, ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }
}
