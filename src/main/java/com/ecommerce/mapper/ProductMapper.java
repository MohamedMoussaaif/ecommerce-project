package com.ecommerce.mapper;

import com.ecommerce.dto.productDTO.RequestProduct;
import com.ecommerce.dto.userDTO.UpdateUserDto;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName")
    RequestProduct toRequestProduct(Product product);

    @Mapping(target = "category", ignore = true)
    Product toProduct(RequestProduct product);

    void updateRequestToProduct(RequestProduct dto, @MappingTarget Product product);
}
