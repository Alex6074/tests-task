package ru.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private UUID id;
    private String firstName;
    private String lastName;
    private OffsetDateTime birthDate;
}
