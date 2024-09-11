package ru.clevertec.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    private UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException byUserId(UUID id) {
        return new UserNotFoundException(
                String.format("User not found by id %s", id)
        );
    }
}
