package com.example.copsboot.user.web;

import com.example.copsboot.user.User;

import java.util.UUID;

public record UserDto(UUID userId, String email, UUID authServerId, String mobileToken) {
    public static UserDto fromUser(User user) {
        return new UserDto(user.getId().getId(),
                user.getEmail(),
                user.getAuthServerId().value(),
                user.getMobileToken());
    }
}
