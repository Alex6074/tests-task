package ru.clevertec.util;

import ru.clevertec.helper.DateSupplier;

import java.time.OffsetDateTime;

public class DateSupplierTest implements DateSupplier {
    @Override
    public OffsetDateTime getCurrentDateTime() {
        return OffsetDateTime.parse("2022-08-08T23:23:23.123123Z");
    }
}
