package com.ecommerce.dto.userDTO;

import com.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private User user;
    private String token;
}
