package com.ecommerce.mapper;

import com.ecommerce.dto.cartDTO.RequestOrderItem;
import com.ecommerce.dto.categoryDTO.CategoryRequest;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryRequest toCategoryRequest(Category category);
    Category toCategory(CategoryRequest categoryRequest);
}
