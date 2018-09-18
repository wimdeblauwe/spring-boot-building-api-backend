package com.example.copsboot.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //<1>
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UserId userId) {
        super(String.format("Could not find user with id %s", userId.asString()));
    }
}
