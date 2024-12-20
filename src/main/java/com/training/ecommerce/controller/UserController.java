package com.training.ecommerce.controller;

import com.training.ecommerce.api.UsersApi;
import com.training.ecommerce.model.UserDto;
import com.training.ecommerce.model.UserLoginDto;
import com.training.ecommerce.model.UserRegistrationDto;
import com.training.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController implements UsersApi {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<UserDto> registerUser(final UserRegistrationDto userRegistrationDto) {
        UserDto registeredUser = userService.registerUser(userRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @Override
    public ResponseEntity<UserDto> loginUser(final UserLoginDto userLoginDto) {
         Optional<UserDto> loggedUser = userService.loginUser(userLoginDto);
         return loggedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Override
    public ResponseEntity<UserDto> getUser(final Long id, final Long myRequestParam) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<UserDto> updateUser(final Long id, final UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @Override
    public ResponseEntity<Void> deleteUser(final Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
