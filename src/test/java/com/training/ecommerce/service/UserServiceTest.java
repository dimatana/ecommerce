package com.training.ecommerce.service;

import com.training.ecommerce.dto.UserDTO;
import com.training.ecommerce.dto.UserRegistrationDTO;
import com.training.ecommerce.mapper.UserMapper;
import com.training.ecommerce.model.User;
import com.training.ecommerce.repository.UserRepository;
import io.micrometer.observation.Observation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
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

        UserRegistrationDTO userRegistrationDTO = mock(UserRegistrationDTO.class);
        final User user = mock(User.class);
        final UserDTO userDTO = mock(UserDTO.class);

        when(userRegistrationDTO.getEmail()).thenReturn("tana");
        when(userRepository.findByEmail("tana")).thenReturn(null);
        when(userMapper.toEntity(userRegistrationDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO registeredUser = userService.registerUser(userRegistrationDTO);

        assertEquals(registeredUser, userDTO);
    }

    @Test
    public void testRegisterUserThrowExceptionWhenEmailExists() {
        UserRegistrationDTO userRegistrationDTO = mock(UserRegistrationDTO.class);

        when(userRegistrationDTO.getEmail()).thenReturn("tana");
        when(userRepository.findByEmail("tana")).thenReturn(mock(User.class));

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userRegistrationDTO));
    }

    @Test
    public void testFindByEmailAndPassword() {
        User user = new User();
        user.setId(1L);
        user.setEmail("tana");
        user.setName("dima");
        user.setAddress("stefan 55");
        user.setPassword("1234");

        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress());

        when(userRepository.findByEmailAndPassword("tana", "1234")).thenReturn(user);

        UserDTO foundUserDTO = userService.findByEmailAndPassword("tana", "1234");

        assertNotNull(foundUserDTO);
        assertEquals(userDTO.getId(), foundUserDTO.getId());
        assertEquals(userDTO.getName(), foundUserDTO.getName());
        assertEquals(userDTO.getEmail(), foundUserDTO.getEmail());
        assertEquals(userDTO.getAddress(), foundUserDTO.getAddress());

        verify(userRepository).findByEmailAndPassword("tana", "1234");
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("user1");
        user1.setEmail("user1@gmail");
        user1.setAddress("boico");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("user2");
        user2.setEmail("user2@gmail");
        user2.setAddress("independentei");

        //mock-uim repository sa returneze o lista de User
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        //cream o lista de UserDTO asteptata
        List<UserDTO> expectedUserDTOList = List.of(
                new UserDTO(user1.getId(), user1.getName(), user1.getEmail(), user1.getAddress()),
                new UserDTO(user2.getId(), user2.getName(), user2.getEmail(), user2.getAddress()));

        // apelam metoda din service care ar trebui sa returneze o lista de User
        List<UserDTO> foundUsers = userService.getAllUsers();

        //verificam daca lista asteptata si lista returnata sunt egale
        assertEquals(expectedUserDTOList, foundUsers);

        //verificam daca repository-ul a fost apelat
        verify(userRepository).findAll();
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setEmail("tana");
        user.setName("dima");
        user.setAddress("stefan 55");
        user.setPassword("1234");
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserDTO> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(userDTO.getId(), foundUser.get().getId());
        verify(userRepository).findById(1L);
    }

    @Test
    public void testUpdateUser() {
        UserDTO userDTO = mock(UserDTO.class);
        User userDetails = mock(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userDetails));
        when(userRepository.save(any(User.class))).thenReturn(userDetails);

        UserDTO updateUser = userService.updateUser(1L, userDTO);
        verify(userRepository).findById(1L);
        verify(userRepository).save(userDetails);
    }

    @Test
    public void testUpdateUserThrowsExceptionWhenUserNotFound() {
        UserDTO userDetails = mock(UserDTO.class);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userService.updateUser(1L, userDetails);
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
