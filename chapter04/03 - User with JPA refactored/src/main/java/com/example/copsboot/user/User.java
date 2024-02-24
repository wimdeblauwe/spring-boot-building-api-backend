package com.example.copsboot.user;

import com.example.orm.jpa.AbstractEntity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

//tag::class-def[]
@Entity
@Table(name = "copsboot_user")
public class User extends AbstractEntity<UserId> {
//end::class-def[]

    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<UserRole> roles;

    protected User() {

    }

    //tag::constructor[]
    public User(UserId id, String email, String password, Set<UserRole> roles) {
        super(id);
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    //end::constructor[]

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
