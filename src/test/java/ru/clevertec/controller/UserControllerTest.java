package ru.clevertec.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.domain.User;
import ru.clevertec.exception.UserNotFoundException;
import ru.clevertec.service.UserService;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFindAllUsers() throws Exception {
        //given
        when(userService.getUsers())
                .thenReturn(List.of(new User(), new User()));

        //when, then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldFindUserById() throws Exception {
        //given
        User user = TestData.generateUser();
        UUID userId = user.getId();
        when(userService.getUserById(userId))
                .thenReturn(user);

        //when, then
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.birthDate").value(user.getBirthDate().toString()));
    }

    @Test
    void shouldReturnNotFoundStatus_whenUserNotFound() throws Exception {
        //given
        UUID userId = UUID.randomUUID();
        when(userService.getUserById(userId))
                .thenThrow(UserNotFoundException.byUserId(userId));

        //when, then
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldCreateUser() throws Exception {
        //given
        User user = TestData.generateUser();
        UUID userId = user.getId();
        when(userService.create(user))
                .thenReturn(user);

        //when, then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.birthDate").value(user.getBirthDate().toString()));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        //given
        User user = TestData.generateUser();
        UUID userId = user.getId();
        when(userService.update(userId, user))
                .thenReturn(user);

        //when, then
        mockMvc.perform(put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.birthDate").value(user.getBirthDate().toString()));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        //given
        UUID userId = UUID.randomUUID();

        //when, then
        mockMvc.perform(delete("/users/{userId}", userId))
                .andExpect(status().isOk());
    }
}