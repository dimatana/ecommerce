package com.training.ecommerce.controller;

import com.training.ecommerce.api.UsersApi;
import com.training.ecommerce.dto.UserRegistrationDTO;
import com.training.ecommerce.model.UserDto;
import com.training.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class
UserController implements UsersApi {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO userDTO){
        try {
            UserDto registeredUser = userService.registerUser(userDTO);
            return ResponseEntity.ok(registeredUser);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @GetMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestParam String email, @RequestParam String password) {
        return userService.findByEmailAndPassword(email, password)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/{users}/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        Optional<UserDto> userDto = userService.getUserById(id);
        return userDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,@RequestBody UserDto userDetails){
            UserDto updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    public static class LoginRequest{
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
