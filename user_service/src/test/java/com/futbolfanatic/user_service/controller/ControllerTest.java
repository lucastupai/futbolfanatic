package com.futbolfanatic.user_service.controller;

import com.futbolfanatic.user_service.model.User;
import com.futbolfanatic.user_service.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAllUsers_ShouldReturnUsers() throws Exception {
        User u1 = new User(1L, "user1@example.com", "pass1", "User One");
        User u2 = new User(2L, "user2@example.com", "pass2", "User Two");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        User user = new User(null, "newuser@example.com", "passnew", "New User");
        User savedUser = new User(1L, "newuser@example.com", "passnew", "New User");

        when(userService.createUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("newuser@example.com"))
                .andExpect(jsonPath("$.name").value("New User"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void deleteUser_ShouldReturnNoContent()throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }
}
