package com.ecommerce.mapper;

import com.ecommerce.dto.cartDTO.RequestOrderItem;
import com.ecommerce.dto.productDTO.RequestProduct;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    RequestOrderItem toRequestOrderItem(OrderItem orderItem);

    OrderItem toOrderItem(RequestOrderItem requestOrderItem);
}
