package com.example.copsboot.user;

public record CreateUserParameters(AuthServerId authServerId, String email, String mobileToken) {
}
