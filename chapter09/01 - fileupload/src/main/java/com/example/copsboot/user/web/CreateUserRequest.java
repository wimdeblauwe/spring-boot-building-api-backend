package com.example.copsboot.user.web;

import com.example.copsboot.user.AuthServerId;
import com.example.copsboot.user.CreateUserParameters;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

@ValidCreateUserRequest
public record CreateUserRequest(@NotEmpty String mobileToken) { //<.>

    public CreateUserParameters toParameters(Jwt jwt) {
        AuthServerId authServerId = new AuthServerId(UUID.fromString(jwt.getSubject()));
        String email = jwt.getClaimAsString("email");
        return new CreateUserParameters(authServerId, email, mobileToken);
    }
}
