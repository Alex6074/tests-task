package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.domain.User;
import ru.clevertec.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<User> toDomains(List<UserEntity> users);

    User toDomain(UserEntity userEntity);

    UserEntity toEntity(User user);
}
