package com.training.ecommerce.service;

import com.training.ecommerce.mapper.UserMapper;
import com.training.ecommerce.model.User;
import com.training.ecommerce.model.UserDto;
import com.training.ecommerce.model.UserRegistrationDto;
import com.training.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;


    @Test
    void testRegisterUser() {

        UserRegistrationDto userRegistrationDTO = mock(UserRegistrationDto.class);
        final User user = mock(User.class);
        final UserDto userDto = mock(UserDto.class);

        when(userRegistrationDTO.getEmail()).thenReturn("tana");
        when(userRepository.findByEmail("tana")).thenReturn(null);
        when(userMapper.toEntity(userRegistrationDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto registeredUser = userService.registerUser(userRegistrationDTO);

        assertEquals(registeredUser, userDto);
    }

    @Test
    public void testRegisterUserThrowExceptionWhenEmailExists() {
        UserRegistrationDto userRegistrationDTO = mock(UserRegistrationDto.class);

        when(userRegistrationDTO.getEmail()).thenReturn("tana");
        when(userRepository.findByEmail("tana")).thenReturn(mock(User.class));

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userRegistrationDTO));
    }

    @Test
    public void testFindByEmailAndPassword() {
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);

        when(userRepository.findByEmailAndPassword("dima", "1234")).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        Optional<UserDto> result = userService.findByEmailAndPassword("dima", "1234");

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
}

    @Test
    public void testGetAllUsers() {
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        UserDto userDto1 = mock(UserDto.class);
        UserDto userDto2 = mock(UserDto.class);
        List<User> users = Arrays.asList(user1, user2);
        List<UserDto> userDtos = Arrays.asList(userDto1, userDto2);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);

        List<UserDto> result = userService.getAllUsers();
        assertEquals(userDtos.size(), result.size());
        assertEquals(userDtos.get(0), userDtos.get(0));
        assertEquals(userDtos.get(1), userDtos.get(1));

    }
    @Test
    public void testGetUserById() {
      //pregatim datele de test
        User user = mock(User.class);
        UserDto userDto = mock(UserDto.class);

        //definim comportamentul mock-urilor
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        //apelam metoda de testat
        UserDto result = userService.getUserById(1L);

        //verificam rezultatul
        assertEquals(userDto, result);
    }

    @Test
    public void testGetUserById_when_not_found() {
        //definim comportamentul mock-urilor
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //apelam metoda de testat
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    public void testUpdateUser() {
        User user = mock(User.class);
        UserDto userDetails = new UserDto();
        userDetails.setName("dima");
        userDetails.setEmail("dima@gmail.com");
        userDetails.setAddress("dima cel mare 25");
        UserDto updatedUserDto = mock(UserDto.class);


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(updatedUserDto);

        UserDto result = userService.updateUser(1L, userDetails);

        assertEquals(updatedUserDto, result);
        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
        verify(userRepository).save(user);

        verify(user).setName("dima");
        verify(user).setEmail("dima@gmail.com");
        verify(user).setAddress("dima cel mare 25");
    }

    @Test
    public void testUpdateUserThrowsExceptionWhenUserNotFound() {
        UserDto userDetails = mock(UserDto.class);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
        userService.updateUser(1L, userDetails);
        });
    }

    @Test
    public void testDeleteUser() {
        User user = mock(User.class);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    public void testDeleteUserThrowsExceptionWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }
}
