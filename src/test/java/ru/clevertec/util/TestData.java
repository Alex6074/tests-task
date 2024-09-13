package ru.clevertec.util;

import ru.clevertec.domain.User;
import ru.clevertec.entity.UserEntity;
import ru.clevertec.helper.DateSupplier;

import java.util.UUID;

public class TestData {

    private static final DateSupplier DATE_SUPPLIER = new DateSupplierTest();

    public static UserEntity generateUserEntity() {
        return new UserEntity(UUID.randomUUID(), "Dmitry", "Sidorov", DATE_SUPPLIER.getCurrentDateTime().plusMonths(2));
    }

    public static User generateUser() {
        return new User(UUID.randomUUID(), "Dmitry", "Sidorov", DATE_SUPPLIER.getCurrentDateTime().plusMonths(2));
    }
}
