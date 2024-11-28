package com.training.ecommerce.service;

import com.training.ecommerce.exception.ResourceNotFoundException;
import com.training.ecommerce.mapper.UserMapper;
import com.training.ecommerce.model.UserDto;
import com.training.ecommerce.model.UserLoginDto;
import com.training.ecommerce.model.UserRegistrationDto;
import com.training.ecommerce.repository.UserRepository;
import com.training.ecommerce.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(final UserRepository userRepository, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    //register new user DTO
    @Transactional
    public UserDto registerUser(UserRegistrationDto userRegistrationDto){
        if (userRepository.findByEmail(userRegistrationDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already registered");
        }
        final User user = userMapper.toEntity(userRegistrationDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
    //login user
    public Optional<UserDto> loginUser(UserLoginDto userLoginDto) {
        return Optional.ofNullable(userRepository.findByEmailAndPassword("dima", "1234"))
                .map(userMapper::toDto);
    }
    //get all users DTO
    public List<UserDto> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    //get a user by id
    public UserDto getUserById(Long id){
        //todo look at the @ControllerAdvice
        return userRepository.findById(id)
                .map(userMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("user with id %s not found".formatted(id)));
    }
    //update user details
    public UserDto updateUser(Long id, UserDto userDetails){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::" +id));

        Optional.ofNullable(userDetails.getName()).ifPresent(user::setName);
        Optional.ofNullable(userDetails.getAddress()).ifPresent(user::setAddress);
        Optional.ofNullable(userDetails.getEmail()).ifPresent(user::setEmail);

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }
    //delete a user
    public void deleteUser(Long id) {
        final User user = userRepository.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::"
                                                                                         + id));
        userRepository.delete(user);
    }

}
