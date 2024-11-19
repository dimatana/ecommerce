package com.training.ecommerce.serviceTest;

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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {
   @InjectMocks
    private UserService userService;

   @Mock
    private UserRepository userRepository;

   @Before
    public void setUp(){
       MockitoAnnotations.openMocks(this);
   }
   @Test
    public void testRegisterUser(){
       User user = mock(User.class);
       when(user.getEmail()).thenReturn("tana");
       when(userRepository.findByEmail("tana")).thenReturn(null);
       when(userRepository.save(user)).thenReturn(user);

       User registeredUser = userService.registerUser(user);
       verify(userRepository).findByEmail("tana");
       verify(userRepository).save(user);
       Assert.assertEquals("tana", registeredUser.getEmail());
   }
   @Test(expected = IllegalArgumentException.class)
    public void testRegisterUserThrowExceptionWhenEmailExists(){
       User user = mock(User.class);
       when(user.getEmail()).thenReturn("tana");
       when(userRepository.findByEmail("tana")).thenReturn(user);

       userService.registerUser(user);
   }
   @Test
    public void testFindByEmailAndPassword(){
       User user = mock(User.class);
       when(userRepository.findByEmailAndPassword("tana", "1234")).thenReturn(user);

       User foundUser = userService.findByEmailAndPassword("tana", "1234");
       Assert.assertEquals(user, foundUser);
       verify(userRepository).findByEmailAndPassword("tana", "1234");
   }
   @Test
    public void testGetAllUsers(){
       List <User> users = List.of(mock(User.class), mock(User.class));
       when(userRepository.findAll()).thenReturn(users);

       List <User> foundUsers = userService.getAllUsers();
       Assert.assertEquals(users, foundUsers);
       verify(userRepository).findAll();

   }
   @Test
    public void testGetUserById(){
       User user = mock(User.class);
       when(userRepository.findById(1L)).thenReturn(Optional.of(user));

       Optional <User> foundUser = userService.getUserById(1L);

       Assert.assertTrue(foundUser.isPresent());
       Assert.assertEquals(user, foundUser.get());
       verify(userRepository).findById(1L);
   }
   @Test
    public void testUpdateUser(){
       User user = mock(User.class);
       User userDetails = mock(User.class);
       when(userRepository.findById(1L)).thenReturn(Optional.of(user));
       when(userRepository.save(user)).thenReturn(user);

       User updateUser = userService.updateUser(1L, userDetails);
       Assert.assertEquals(updateUser, updateUser);
       verify(userRepository).findById(1L);
       verify(userRepository).save(user);
   }
   @Test(expected = ResourceNotFoundException.class)
    public void testUpdateUserThrowsExceptionWhenUserNotFound(){
       User userDetails = mock(User.class);
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
