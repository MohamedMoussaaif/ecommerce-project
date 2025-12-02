package com.ecommerce.mapper;

import com.ecommerce.dto.userDTO.RequestUser;
import com.ecommerce.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    RequestUser toRequestUser(User user);
    User toUser(RequestUser user);
}
