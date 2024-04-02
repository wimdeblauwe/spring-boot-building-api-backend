package com.example.copsboot.user.web;

import com.example.copsboot.user.AuthServerId;
import com.example.copsboot.user.CreateUserParameters;
import com.example.copsboot.user.User;
import com.example.copsboot.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // tag::myself[]
    @GetMapping("/me") //<.>
    public Map<String, Object> myself(@AuthenticationPrincipal Jwt jwt) { //<.>
        Optional<User> userByAuthServerId = userService.findUserByAuthServerId(new AuthServerId(UUID.fromString(jwt.getSubject())));

        Map<String, Object> result = new HashMap<>();
        userByAuthServerId.ifPresent(user -> result.put("userId", user.getId().asString()));
        result.put("subject", jwt.getSubject());
        result.put("claims", jwt.getClaims());

        return result;
    }
    // end::myself[]

    // tag::createUser[]
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('OFFICER')")
    public UserDto createUser(@AuthenticationPrincipal Jwt jwt,
                              @Valid @RequestBody CreateUserRequest request) {
        CreateUserParameters parameters = request.toParameters(jwt);
        User user = userService.createUser(parameters);
        return UserDto.fromUser(user);
    }
    // end::createUser[]
}
