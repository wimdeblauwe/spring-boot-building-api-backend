package com.example.copsboot.user;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

//tag::annotations-part[]
@Entity //<1>
@Table(name = "copsboot_user") //<2>
public class User {
    @Id
    private UUID id; //<3>

    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<UserRole> roles; //<4>

    protected User() { //<5>

    }

    public User(UUID id, String email, String password, Set<UserRole> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    //end::annotations-part[]

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
