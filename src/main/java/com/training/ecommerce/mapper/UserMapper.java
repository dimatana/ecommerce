package com.training.ecommerce.mapper;

import com.training.ecommerce.model.User;
import com.training.ecommerce.model.UserDto;
import com.training.ecommerce.model.UserRegistrationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    User toEntity(UserRegistrationDto userRegistrationDto);
}
