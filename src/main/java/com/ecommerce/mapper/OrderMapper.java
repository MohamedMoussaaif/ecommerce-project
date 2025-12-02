package com.ecommerce.mapper;

import com.ecommerce.dto.orderDTO.RequestOrder;
import com.ecommerce.dto.productDTO.RequestProduct;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    RequestOrder toRequestOrder(Order order);

    @Mapping(target = "items", ignore = true)
    Order toOrder(RequestOrder order);
}
