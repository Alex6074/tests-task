package ru.clevertec.mapper;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.domain.User;
import ru.clevertec.entity.UserEntity;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    private static final UserMapper userMapper = new UserMapperImpl();
    private static final EasyRandom easyRandom = new EasyRandom();

    @ParameterizedTest
    @MethodSource("userEntityProvider")
    void testToDomain(UserEntity userEntity) {
        //when
        User user = userMapper.toDomain(userEntity);

        //then
        assertNotNull(user);
        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getFirstName(), user.getFirstName());
    }

    @ParameterizedTest
    @MethodSource("userProvider")
    void testToEntity(User user) {
        //when
        UserEntity userEntity = userMapper.toEntity(user);

        //then
        assertNotNull(userEntity);
        assertEquals(user.getId(), userEntity.getId());
        assertEquals(user.getFirstName(), userEntity.getFirstName());
    }

    @ParameterizedTest
    @MethodSource("userEntityListProvider")
    void testToDomains(List<UserEntity> userEntities) {
        //when
        List<User> users = userMapper.toDomains(userEntities);

        //then
        assertNotNull(users);
        assertEquals(userEntities.size(), users.size());
        for (int i = 0; i < userEntities.size(); i++) {
            assertEquals(userEntities.get(i).getId(), users.get(i).getId());
        }
    }

    static Stream<Arguments> userEntityProvider() {
        return easyRandom.objects(UserEntity.class, 10).map(Arguments::of);
    }

    static Stream<Arguments> userProvider() {
        return easyRandom.objects(User.class, 10).map(Arguments::of);
    }

    static Stream<Arguments> userEntityListProvider() {
        List<UserEntity> userEntities = easyRandom.objects(UserEntity.class, 5).toList();
        return Stream.of(Arguments.of(userEntities));
    }
}