package com.example.copsboot.user;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;
//tag::class[]
public interface UserRepository extends CrudRepository<User, UUID>, UserRepositoryCustom {
}
//end::class[]
