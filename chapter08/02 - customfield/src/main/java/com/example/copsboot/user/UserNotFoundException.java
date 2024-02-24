package com.example.copsboot.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UserId userId) {
        super(String.format("Unable to find user with id %s", userId));
    }

    public UserNotFoundException(AuthServerId authServerId) {
        super(String.format("Unable to find user with auth server id %s", authServerId));
    }
}
