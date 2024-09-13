package ru.clevertec.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.domain.User;
import ru.clevertec.entity.UserEntity;
import ru.clevertec.exception.UserNotFoundException;
import ru.clevertec.mapper.UserMapper;
import ru.clevertec.repository.UserRepository;
import ru.clevertec.util.DateSupplierTest;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Spy
    private DateSupplierTest dateSupplierTest;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldGetUsers() {
        //given
        List<UserEntity> userEntities = List.of(TestData.generateUserEntity());

        List<User> users = List.of(new User());
        when(userRepository.getUsers())
                .thenReturn(userEntities);
        when(userMapper.toDomains(userEntities))
                .thenReturn(users);

        //when
        List<User> actualUsers = userService.getUsers();
        //then
        assertFalse(actualUsers.isEmpty());
        assertEquals(1, actualUsers.size());
    }

    @Test
    void shouldGetUserById() {
        //given
        UUID userId = UUID.randomUUID();

        UserEntity userEntity = TestData.generateUserEntity();
        User user = new User();
        user.setId(UUID.randomUUID());

        when(userRepository.getUserById(userId))
                .thenReturn(Optional.of(userEntity));
        when(userMapper.toDomain(userEntity))
                .thenReturn(user);

        //when
        User actualUser = userService.getUserById(userId);

        //then
        assertEquals(user.getId(), actualUser.getId());
        verify(userRepository).getUserById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowUserNotFoundException_whenUserNotFound() {
        //given
        UUID userId = UUID.randomUUID();

        when(userRepository.getUserById(userId))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(userId)
        );
        verifyNoInteractions(userMapper);
    }

    @Test
    void shouldCreateUser() {
        //given
        UserEntity userEntity = TestData.generateUserEntity();
        User user = new User();
        user.setId(UUID.randomUUID());

        when(userMapper.toEntity(user))
                .thenReturn(userEntity);
        when(userRepository.create(userEntity))
                .thenReturn(userEntity);
        when(userMapper.toDomain(userEntity))
                .thenReturn(user);

        //when
        User actualUser = userService.create(user);

        //then
        assertEquals(user.getId(), actualUser.getId());
    }

    @Test
    void shouldUpdateUser() {
        //given
        UserEntity userEntity = TestData.generateUserEntity();
        User user = new User();
        UUID userId = UUID.randomUUID();
        user.setId(userId);

        when(userMapper.toEntity(user))
                .thenReturn(userEntity);
        when(userRepository.update(userId, userEntity))
                .thenReturn(userEntity);
        when(userMapper.toDomain(userEntity))
                .thenReturn(user);

        //when
        User actualUser = userService.update(userId, user);

        //then
        assertEquals(user.getId(), actualUser.getId());
    }

    @Test
    void shouldDeleteById() {
        //given
        UUID userId = UUID.randomUUID();

        //when
        userService.delete(userId);

        //then
        verify(userRepository).delete(userId);
    }
}