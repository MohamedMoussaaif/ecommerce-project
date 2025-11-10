package com.ecommerce.dto.productDTO;

import com.ecommerce.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestProduct {

    private String productName;
    private String productDescription;
    private double productPrice;
    private int productQuantity;

    private String categoryName;
}
