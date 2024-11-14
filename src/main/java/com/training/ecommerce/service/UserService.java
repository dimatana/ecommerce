package com.training.ecommerce.service;

import com.training.ecommerce.repository.UserRepository;
import com.training.ecommerce.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //register new user
    @Transactional
    public User registerUser(User user){
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw  new IllegalArgumentException("Email already registered");
        }
        return userRepository.save(user);
    }
    //login user
    public User findByEmailAndPassword(String email, String password){
        return userRepository.findByEmailAndPassword(email, password);
    }
    //get all users
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    //get a user by id
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
    //update user details
    public User updateUser(Long id, User userDetails){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::" +id));
        user.setName(userDetails.getName());
        user.setAddress(userDetails.getAddress());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }
    //delete a user
    public void deleteUser(Long id) {
        User user;
        user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::" +id));
        userRepository.delete(user);
    }
}
