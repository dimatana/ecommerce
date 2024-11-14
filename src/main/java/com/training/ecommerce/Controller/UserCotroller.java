package com.training.ecommerce.Controller;

import com.training.ecommerce.Service.UserService;
import com.training.ecommerce.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserCotroller {
    private final UserService userService;

    public UserCotroller(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PutMapping("/{id}")
    public Optional<User> getUserById(Long id){
        return userService.getUserById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        User user = userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok("Login successful");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid credentials");
        }
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
