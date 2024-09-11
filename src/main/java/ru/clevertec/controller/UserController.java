package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.domain.User;
import ru.clevertec.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/v1/users")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(users);
    }
}
