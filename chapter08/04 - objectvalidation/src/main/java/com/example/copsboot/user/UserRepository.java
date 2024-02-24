package com.example.copsboot.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//tag::class[]
public interface UserRepository extends CrudRepository<User, UserId>, UserRepositoryCustom {
    Optional<User> findByAuthServerId(AuthServerId authServerId);
}
//end::class[]
