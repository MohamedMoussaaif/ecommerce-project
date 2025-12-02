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


    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public ResponseEntity<ApiResponse> addItemToCart(long userId, RequestCartItem cartItem) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Cart not found"));

        List<CartItem> items = cart.getItems();

        CartItem targetCartItem = cartItemMapper.toCartItem(cartItem);
        Product product = productRepository.findById(cartItem.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        targetCartItem.setProduct(product);
        targetCartItem.setPriceAtPurchase(targetCartItem.getQuantity() * product.getProductPrice());

        cartItemService.addCartItem(targetCartItem);

        items.add(targetCartItem);

        cart.setItems(items);
        double subtotal = cart.getSubtotal() + targetCartItem.getPriceAtPurchase();
        cart.setSubtotal(subtotal);
        cart.setTotalPrice(subtotal + cart.getShippingCost());
        cartRepository.save(cart);

        return new ResponseEntity<ApiResponse>(new ApiResponse(cart, "Item added successfully", HttpStatus.CREATED.value()),  HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> removeItemFromCart(long userId, long itemId) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Cart not found"));
        CartItem cartItem =  cartItemRepository.findById(itemId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        List<CartItem> items = cart.getItems();
        items.remove(cartItem);
        cartItemRepository.deleteById(itemId);
        cart.setItems(items);

        if(cart.getItems().isEmpty()) {
            cart.setSubtotal(0);
            cart.setTotalPrice(0);
        } else {
            double subtotal = cart.getSubtotal() - cartItem.getPriceAtPurchase();
            cart.setSubtotal(subtotal);
            cart.setTotalPrice(subtotal + cart.getShippingCost());
        }


        return new ResponseEntity<ApiResponse>(new ApiResponse(null, "Item removed successfully", HttpStatus.OK.value()),   HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> removeAllItemsFromCart(long userId) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("Cart not found"));

        List<CartItem> items = cart.getItems();
        for(CartItem cartItem : cart.getItems()) {
            cartItemService.deleteCartItem(cartItem.getId());
        }
        cart.setItems(new ArrayList<>());
        if(cart.getItems().isEmpty()) {
            cart.setSubtotal(0);
            cart.setTotalPrice(0);
        }

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

        double subTotal = item.getProduct().getProductPrice() * quantity;
        cartItemService.updateOrderItem(itemId, quantity);
        cart.setSubtotal(subTotal);
        if(subTotal == 0) {
            cart.setTotalPrice(0);
        } else {
            cart.setTotalPrice(subTotal + cart.getShippingCost());
        }
        cartRepository.save(cart);

        return new ResponseEntity<ApiResponse>(new ApiResponse(null, "Item updated successfully", HttpStatus.OK.value()),  HttpStatus.OK);

    }
}
