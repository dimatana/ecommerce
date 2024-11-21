package com.training.ecommerce.serviceTest;

import com.training.ecommerce.dto.UserDTO;
import com.training.ecommerce.dto.UserRegistrationDTO;
import com.training.ecommerce.model.User;
import com.training.ecommerce.repository.UserRepository;
import com.training.ecommerce.service.ResourceNotFoundException;
import com.training.ecommerce.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {
   @InjectMocks
    private UserService userService;

   @Mock
   private UserRepository userRepository;
   @Mock
   private ModelMapper modelMapper = new ModelMapper();

   @Before
    public void setUp(){
       MockitoAnnotations.openMocks(this);
   }
   @Test
    public void testRegisterUser(){
       UserRegistrationDTO userRegistrationDTO = mock(UserRegistrationDTO.class);
       when(userRegistrationDTO.getEmail()).thenReturn("tana");
       when(userRepository.findByEmail("tana")).thenReturn(null);
       when(userRepository.save(any(User.class))).thenReturn(new User());

       UserDTO registeredUser = userService.registerUser(userRegistrationDTO);
       verify(userRepository).findByEmail("tana");
       verify(userRepository).save(any(User.class));
       Assert.assertEquals("tana", registeredUser.getEmail());
   }
   @Test(expected = IllegalArgumentException.class)
    public void testRegisterUserThrowExceptionWhenEmailExists(){
       UserRegistrationDTO userRegistrationDTO = mock(UserRegistrationDTO.class);
       when(userRegistrationDTO.getEmail()).thenReturn("tana");
       when(userRepository.findByEmail("tana")).thenReturn(mock(User.class));

       userService.registerUser(userRegistrationDTO);
   }
   @Test
    public void testFindByEmailAndPassword(){
      User user = new User();
      user.setId(1L);
      user.setEmail("tana");
      user.setName("dima");
      user.setAddress("stefan 55");
      user.setPassword("1234");

       UserDTO userDTO = new UserDTO(user.getId(), user.getName(),user.getEmail(), user.getAddress());

       when(userRepository.findByEmailAndPassword("tana", "1234")).thenReturn(user);

       UserDTO foundUserDTO = userService.findByEmailAndPassword("tana", "1234");

       Assert.assertNotNull(foundUserDTO);
       Assert.assertEquals(userDTO.getId(), foundUserDTO.getId());
       Assert.assertEquals(userDTO.getName(), foundUserDTO.getName());
       Assert.assertEquals(userDTO.getEmail(), foundUserDTO.getEmail());
       Assert.assertEquals(userDTO.getAddress(), foundUserDTO.getAddress());

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
       List <UserDTO> expectedUserDTOList = List.of(
               new UserDTO(user1.getId(), user1.getName(), user1.getEmail(), user1.getAddress()),
               new UserDTO(user2.getId(), user2.getName(), user2.getEmail(), user2.getAddress())
       );

       // apelam metoda din service care ar trebui sa returneze o lista de User
       List <UserDTO> foundUsers = userService.getAllUsers();

       //verificam daca lista asteptata si lista returnata sunt egale
       Assert.assertEquals(expectedUserDTOList,foundUsers);

       //verificam daca repository-ul a fost apelat
       verify(userRepository).findAll();

   }
   @Test
    public void testGetUserById(){
      User user = new User();
      user.setId(1L);
      user.setEmail("tana");
      user.setName("dima");
      user.setAddress("stefan 55");
      user.setPassword("1234");
      UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAddress());

       when(userRepository.findById(1L)).thenReturn(Optional.of(user));

       Optional <UserDTO> foundUser = userService.getUserById(1L);

       Assert.assertTrue(foundUser.isPresent());
       Assert.assertEquals(userDTO.getId(), foundUser.get().getId());
       verify(userRepository).findById(1L);
   }
   @Test
    public void testUpdateUser(){
       UserDTO userDTO = mock(UserDTO.class);
       User userDetails = mock(User.class);
       when(userRepository.findById(1L)).thenReturn(Optional.of(userDetails));
       when(userRepository.save(any(User.class))).thenReturn(userDetails);

       UserDTO updateUser = userService.updateUser(1L, userDTO);
       verify(userRepository).findById(1L);
       verify(userRepository).save(userDetails);
   }
   @Test(expected = ResourceNotFoundException.class)
    public void testUpdateUserThrowsExceptionWhenUserNotFound(){
       UserDTO userDetails = mock(UserDTO.class);
       when(userRepository.findById(1L)).thenReturn(Optional.empty());

       userService.updateUser(1L, userDetails);
   }
   @Test
    public void testDeleteUser(){
       User user = mock(User.class);
       when(userRepository.findById(1L)).thenReturn(Optional.of(user));
       userService.deleteUser(1L);

       verify(userRepository).findById(1L);
       verify(userRepository).delete(user);
   }
   @Test(expected = ResourceNotFoundException.class)
    public void testDeleteUserThrowsExceptionWhenUserNotFound(){
       when(userRepository.findById(1L)).thenReturn(Optional.empty());
       userService.deleteUser(1L);
   }


}
