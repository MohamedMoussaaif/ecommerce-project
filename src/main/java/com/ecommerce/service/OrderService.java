package com.ecommerce.service;

import com.ecommerce.dto.orderDTO.OrderStatusRequest;
import com.ecommerce.dto.orderDTO.RequestOrder;
import com.ecommerce.dto.rabbit.OrderCreatedEvent;
import com.ecommerce.dto.userDTO.UpdateUserDto;
import com.ecommerce.entity.*;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.exception.OrderNotFoundException;
import com.ecommerce.exception.UserNotFoundException;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.mapper.rabbit.OrderCreatedEventMapper;
import com.ecommerce.rabbit.OrderProducer;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.utility.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderProducer  orderProducer;
    private final OrderCreatedEventMapper orderCreatedEventMapper;



    public OrderItem cartItemToOrderItem(CartItem cartItem) {
        OrderItem item = new  OrderItem();
        item.setProduct(cartItem.getProduct());
        item.setQuantity(cartItem.getQuantity());
        item.setPriceAtPurchase(cartItem.getPriceAtPurchase());
        orderItemRepository.save(item);
        return item;
    }


    @Transactional
    public ResponseEntity<ApiResponse> checkout(long userId, RequestOrder requestOrder) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with Id : " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new UserNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(null, "Cart is empty", HttpStatus.BAD_REQUEST.value()));
        }

        Order order = orderMapper.toOrder(requestOrder);
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();
        double subTotal = 0;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getPriceAtPurchase());
            orderItem.setOrder(order);

            subTotal += cartItem.getPriceAtPurchase();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setSubtotal(subTotal);
        order.setTotalAmount(subTotal + order.getShippingCost());

        orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        OrderCreatedEvent event = orderCreatedEventMapper.orderToCreatedEvent(order);
        orderProducer.sendOrderMessage(event);

        return ResponseEntity.ok(new ApiResponse(order, "Order created successfully", 200));
    }


    public ResponseEntity<ApiResponse> getAllOrders() {

        List<Order> orders = orderRepository.findAll();

        return new ResponseEntity<ApiResponse>(new ApiResponse(orders,"Orders found", HttpStatus.OK.value()), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> updateOrderStatus(long orderId, OrderStatusRequest orderStatusRequest) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("order not found with Id : " + orderId));

        OrderStatus orderStatus = OrderStatus.valueOf(orderStatusRequest.getOrderStatus());
        order.setOrderStatus(orderStatus);

        orderRepository.save(order);

        return new ResponseEntity<ApiResponse>(new ApiResponse(order,"Order updated successfully", HttpStatus.OK.value()), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> removeOrder(long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("order not found with Id : " + orderId));
        orderRepository.delete(order);

        return new ResponseEntity<ApiResponse>(new ApiResponse(order,"Order removed successfully", HttpStatus.OK.value()), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> orderById(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("order not found with Id : " + orderId));
        return new ResponseEntity<ApiResponse>(new ApiResponse(order,"Order found", HttpStatus.OK.value()), HttpStatus.OK);
    }
}
