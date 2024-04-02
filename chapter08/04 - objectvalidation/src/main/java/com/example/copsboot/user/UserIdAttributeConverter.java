package com.example.copsboot.user;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = true)
public class UserIdAttributeConverter implements AttributeConverter<UserId, UUID> {
    @Override
    public UUID convertToDatabaseColumn(UserId attribute) {
        return attribute.getId();
    }

    @Override
    public UserId convertToEntityAttribute(UUID dbData) {
        return new UserId(dbData);
    }
}
