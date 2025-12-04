package com.ecommerce.mapper;

import com.ecommerce.dto.userDTO.RequestUser;
import com.ecommerce.dto.userDTO.UpdateUserDto;
import com.ecommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    RequestUser toRequestUser(User user);
    User toUser(RequestUser user);

    UpdateUserDto toUpdateUserDto(User user);
    void updateRequestToUser(UpdateUserDto dto, @MappingTarget User user);
}
