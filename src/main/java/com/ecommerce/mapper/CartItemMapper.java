package com.ecommerce.mapper;

import com.ecommerce.dto.cartDTO.RequestCartItem;
import com.ecommerce.dto.cartDTO.RequestOrderItem;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(source = "product.id", target = "productId")
    RequestCartItem toRequestCartItem(CartItem cartItem);

    CartItem toCartItem(RequestCartItem requestCartItem);
}
