package com.training.ecommerce.service;

import com.training.ecommerce.dto.UserDTO;
import com.training.ecommerce.dto.UserRegistrationDTO;
import com.training.ecommerce.repository.UserRepository;
import com.training.ecommerce.model.User;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    //register new user DTO
    @Transactional
    public UserDTO registerUser(UserRegistrationDTO userDTO){
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw  new IllegalArgumentException("Email already registered");
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setAddress(userDTO.getAddress());
        userDTO.setEmail(userDTO.getEmail());
        userDTO.setPassword(userDTO.getPassword());

        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getAddress());
    }
    //login user
    public UserDTO findByEmailAndPassword(String email, String password){
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null){
            return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress());
        }
        return null;
    }
    //get all users DTO
    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress()))
                .collect(Collectors.toList());
    }
    //get a user by id
    public Optional<UserDTO> getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDTO userDTO = new UserDTO(user.get().getId(), user.get().getName(), user.get().getEmail(), user.get().getAddress());
            return Optional.of(userDTO);
        }else {
            return Optional.empty(); //daca utilizatorul nu exista
        }
    }
    //update user details
    public UserDTO updateUser(Long id, UserDTO userDetails){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::" +id));

        if (userDetails.getName() != null){
            user.setName(userDetails.getName());
        }
        if (userDetails.getEmail() != null){
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getAddress() != null){
            user.setAddress(userDetails.getAddress());
        }
        User updateUser =userRepository.save(user);
        return new UserDTO(updateUser.getId(), updateUser.getName(), updateUser.getEmail(), updateUser.getAddress());
    }
    //delete a user
    public void deleteUser(Long id) {
        User user;
        user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::" +id));
        userRepository.delete(user);
    }
}
