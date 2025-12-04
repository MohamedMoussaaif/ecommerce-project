package com.ecommerce.service;

import com.ecommerce.dto.cartDTO.RequestCartItem;
import com.ecommerce.dto.cartDTO.RequestOrderItem;
import com.ecommerce.entity.*;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.exception.UserNotFoundException;
import com.ecommerce.mapper.CartItemMapper;
import com.ecommerce.mapper.OrderItemMapper;
import com.ecommerce.repository.*;
import com.ecommerce.utility.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CartService {

    private CartItemService cartItemService;
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;
    private CartItemMapper cartItemMapper;



    private void recalcCartTotals(Cart cart) {
        double subtotal = 0;

        for (CartItem item : cart.getItems()) {
            subtotal += item.getPriceAtPurchase();
        }

        cart.setSubtotal(subtotal);
        cart.setTotalPrice(subtotal == 0 ? 0 : subtotal + cart.getShippingCost());
    }

    public ResponseEntity<ApiResponse> addItemToCart(long userId, RequestCartItem cartItem) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Cart not found"));
        Product product = productRepository.findById(cartItem.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        List<CartItem> items = cart.getItems();

        boolean productExisted = false;
        CartItem existedItem = null;

        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                productExisted = true;
                existedItem = item;
            }
        }

        if (productExisted) {
            return updateItem(userId, existedItem.getId(),existedItem.getQuantity() + 1);
        }

        CartItem newCartItem = cartItemMapper.toCartItem(cartItem);
        newCartItem.setProduct(product);
        newCartItem.setPriceAtPurchase(newCartItem.getQuantity() * product.getProductPrice());
        newCartItem.setCart(cart);

        cart.getItems().add(newCartItem);

        recalcCartTotals(cart);

        cartRepository.save(cart);

        return new ResponseEntity<ApiResponse>(new ApiResponse(cart, "Item added successfully", HttpStatus.CREATED.value()),  HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> removeItemFromCart(long userId, long itemId) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Cart not found"));
        CartItem cartItem =  cartItemRepository.findById(itemId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        cart.getItems().remove(cartItem);

        recalcCartTotals(cart);
        cartRepository.save(cart);


        return new ResponseEntity<ApiResponse>(new ApiResponse(null, "Item removed successfully", HttpStatus.OK.value()),   HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> removeAllItemsFromCart(long userId) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Cart not found"));

        cart.getItems().clear();

        recalcCartTotals(cart);
        cartRepository.save(cart);

        return new ResponseEntity<ApiResponse>(new ApiResponse(cart, "Items removed successfully", HttpStatus.OK.value()),  HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> getCart(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Cart not found"));
        return new ResponseEntity<ApiResponse>(new ApiResponse(cart, "Cart found", HttpStatus.OK.value()),  HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> updateItem(long userId, long itemId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart  = cartRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Cart not found"));
        CartItem item =  cartItemRepository.findById(itemId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(quantity);
            item.setPriceAtPurchase(item.getProduct().getProductPrice() * quantity);
        }

        recalcCartTotals(cart);
        cartRepository.save(cart);

        return new ResponseEntity<ApiResponse>(new ApiResponse(null, "Item updated successfully", HttpStatus.OK.value()),  HttpStatus.OK);

    }
}
