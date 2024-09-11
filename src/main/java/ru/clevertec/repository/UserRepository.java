package ru.clevertec.repository;

import org.springframework.stereotype.Repository;
import ru.clevertec.entity.UserEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {

    private static final List<UserEntity> db = List.of(
            new UserEntity(UUID.randomUUID(), "Anna", "Petrova", OffsetDateTime.now()),
            new UserEntity(UUID.randomUUID(), "Dmitry", "Sidorov", OffsetDateTime.now()),
            new UserEntity(UUID.randomUUID(), "Olga", "Kuznetsova", OffsetDateTime.now()),
            new UserEntity(UUID.randomUUID(), "Sergey", "Smirnov", OffsetDateTime.now()),
            new UserEntity(UUID.randomUUID(), "Elena", "Voronina", OffsetDateTime.now())
    );

    public List<UserEntity> getUsers() {
        return db;
    }

    public Optional<UserEntity> getUserById(UUID id) {
        return db.stream()
                .filter(userEntity -> userEntity.getId().equals(id))
                .findFirst();
    }

    public UserEntity create(UserEntity userEntity) {
        return userEntity;
    }

    public UserEntity update(UUID id, UserEntity newUserEntity) {
        return newUserEntity.setId(id);
    }

    public void delete(UUID id) {
        //without body
    }
}
