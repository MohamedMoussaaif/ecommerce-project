package com.ecommerce.mapper;

import com.ecommerce.dto.productDTO.RequestProduct;
import com.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName")
    RequestProduct toRequestProduct(Product product);

    @Mapping(target = "category", ignore = true)
    Product toProduct(RequestProduct product);
}
