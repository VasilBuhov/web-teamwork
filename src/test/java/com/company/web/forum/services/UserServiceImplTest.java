package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.User;
import com.company.web.forum.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        when(userRepository.getAllUsers()).thenReturn(Collections.singletonList(new User()));

        // Act
        List<User> users = userService.getAllUsers();

        // Assert
        assertEquals(1, users.size());
        verify(userRepository, times(1)).getAllUsers();
    }



    @Test
    void createUser_ShouldThrowEntityDuplicateException_WhenDuplicateEmail() {
        // Arrange
        User user = new User();
        user.setEmail("existing@example.com");
        when(userRepository.getUserByEmail(anyString())).thenReturn(new User());

        // Act, Assert
        assertThrows(EntityDuplicateException.class, () -> userService.createUser(user));
    }

    // Add more test cases for other methods in UserServiceImpl
    // ...

}
