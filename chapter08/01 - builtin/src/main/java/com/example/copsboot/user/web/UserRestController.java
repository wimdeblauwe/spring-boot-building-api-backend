package com.example.copsboot.user.web;

import com.example.copsboot.infrastructure.security.ApplicationUserDetails;
import com.example.copsboot.user.User;
import com.example.copsboot.user.UserNotFoundException;
import com.example.copsboot.user.UserService;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService service;

    @Autowired
    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public UserDto currentUser(@AuthenticationPrincipal ApplicationUserDetails userDetails) {
        User user = service.getUser(userDetails.getUserId())
                           .orElseThrow(() -> new UserNotFoundException(userDetails.getUserId()));
        return UserDto.fromUser(user);
    }

    //tag::post[]
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createOfficer(@Valid @RequestBody CreateOfficerParameters parameters) {
        User officer = service.createOfficer(parameters.getEmail(),
                                             parameters.getPassword());
        return UserDto.fromUser(officer);
    }
    //end::post[]
}
