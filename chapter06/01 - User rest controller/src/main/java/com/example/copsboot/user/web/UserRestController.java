package com.example.copsboot.user.web;

import com.example.copsboot.infrastructure.security.ApplicationUserDetails;
import com.example.copsboot.user.User;
import com.example.copsboot.user.UserNotFoundException;
import com.example.copsboot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //<1>
@RequestMapping("/api/users") //<2>
public class UserRestController {

    private final UserService service;

    @Autowired
    public UserRestController(UserService service) { //<3>
        this.service = service;
    }

    @GetMapping("/me") //<4>
    public UserDto currentUser(@AuthenticationPrincipal ApplicationUserDetails userDetails) { //<5>
        User user = service.getUser(userDetails.getUserId()) //<6>
                           .orElseThrow(() -> new UserNotFoundException(userDetails.getUserId()));
        return UserDto.fromUser(user); //<7>
    }
}
