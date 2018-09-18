package com.example.copsboot.user;

import java.util.Set;
import java.util.UUID;

public class User {
    private UUID id;
    private String email;
    private String password;
    private Set<UserRole> roles;

    public User(UUID id, String email, String password, Set<UserRole> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }
}
