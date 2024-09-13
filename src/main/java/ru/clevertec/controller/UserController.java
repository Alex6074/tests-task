package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.domain.User;
import ru.clevertec.exception.UserNotFoundException;
import ru.clevertec.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable("userId") UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.create(user);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") UUID userId, @RequestBody User user) {
        User newUser = userService.update(userId, user);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") UUID userId) {
        userService.delete(userId);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
