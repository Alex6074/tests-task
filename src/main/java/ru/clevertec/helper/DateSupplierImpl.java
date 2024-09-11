package ru.clevertec.helper;

import java.time.OffsetDateTime;

public class DateSupplierImpl implements DateSupplier {
    @Override
    public OffsetDateTime getCurrentDateTime() {
        return OffsetDateTime.now();
    }
}
