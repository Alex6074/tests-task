package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.domain.User;
import ru.clevertec.entity.UserEntity;
import ru.clevertec.exception.UserNotFoundException;
import ru.clevertec.helper.DateSupplier;
import ru.clevertec.mapper.UserMapper;
import ru.clevertec.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final DateSupplier dateSupplier;

    public List<User> getUsers() {
        List<UserEntity> users = userRepository.getUsers().stream()
                .filter(userEntity -> userEntity.getBirthDate().isAfter(dateSupplier.getCurrentDateTime()))
                .toList();
        return userMapper.toDomains(users);
    }

    public User getUserById(UUID id) {
        UserEntity user = userRepository.getUserById(id)
                .orElseThrow(() -> UserNotFoundException.byUserId(id));
        return userMapper.toDomain(user);
    }

    public User create(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity createdUserEntity = userRepository.create(userEntity);
        return userMapper.toDomain(createdUserEntity);
    }

    public User update(UUID id, User newUser) {
        UserEntity userEntity = userMapper.toEntity(newUser);
        UserEntity updatedUserEntity = userRepository.update(id, userEntity);
        return userMapper.toDomain(updatedUserEntity);
    }

    public void delete(UUID id) {
        userRepository.delete(id);
    }
}