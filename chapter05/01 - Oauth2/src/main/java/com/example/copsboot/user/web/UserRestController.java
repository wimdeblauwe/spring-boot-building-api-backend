package com.example.copsboot.user.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @GetMapping("/me") //<.>
    public Map<String, Object> myself(@AuthenticationPrincipal Jwt jwt) { //<.>
        return Map.of("subject", jwt.getSubject(), //<.>
                "claims", jwt.getClaims());
    }
}
