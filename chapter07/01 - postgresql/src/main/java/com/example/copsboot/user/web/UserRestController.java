package com.example.copsboot.user.web;

import com.example.copsboot.infrastructure.security.ApplicationUserDetails;
import com.example.copsboot.user.User;
import com.example.copsboot.user.UserNotFoundException;
import com.example.copsboot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @PostMapping //<1>
    @ResponseStatus(HttpStatus.CREATED) //<2>
    public UserDto createOfficer(@Valid @RequestBody CreateOfficerParameters parameters) { //<3>
        User officer = service.createOfficer(parameters.getEmail(), //<4>
                                             parameters.getPassword());
        return UserDto.fromUser(officer); //<5>
    }
    //end::post[]
}
