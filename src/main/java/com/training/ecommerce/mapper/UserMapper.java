package com.training.ecommerce.mapper;

import com.training.ecommerce.dto.UserDTO;
import com.training.ecommerce.dto.UserRegistrationDTO;
import com.training.ecommerce.model.User;
import com.training.ecommerce.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDto toDto(User user);

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);

    User toEntity(UserRegistrationDTO userRegistrationDTO);
}
