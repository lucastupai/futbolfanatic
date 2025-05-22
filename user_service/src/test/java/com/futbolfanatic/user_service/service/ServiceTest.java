package com.futbolfanatic.user_service.service;

import com.futbolfanatic.user_service.model.User;
import com.futbolfanatic.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        User u1 = new User(1L, "user1@example.com", "pass1", "User One");
        User u2 = new User(2L, "user2@example.com", "pass2", "User Two");

        when(userRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUser_ShouldReturnUserWhenFound() {
        User user = new User(1L, "user@example.com", "pass", "User Name");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        User found = userService.getUser(1L);

        assertNotNull(found);
        assertEquals("user@example.com", found.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUser_ShouldReturnNullWhenNotFound() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        User found = userService.getUser(1L);

        assertNull(found);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void createUser_ShouldSaveAndReturnUser() {
        User user = new User(null, "newuser@example.com", "passnew", "New User");
        User savedUser = new User(1L, "newuser@example.com", "passnew", "New User");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("newuser@example.com", result.getEmail());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_ShouldCallDeleteById() {
        Long id = 1L;

        doNothing().when(userRepository).deleteById(id);

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }
}
