package com.example.copsboot.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository; //<.>

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> findUserByAuthServerId(AuthServerId authServerId) { //<.>
        return repository.findByAuthServerId(authServerId);
    }

    // tag::createUser[]
    public User createUser(CreateUserParameters createUserParameters) {
        UserId userId = repository.nextId();
        User user = new User(userId, createUserParameters.email(),
                createUserParameters.authServerId(),
                createUserParameters.mobileToken());
        return repository.save(user);
    }

    public User getUserById(UserId userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public Optional<User> findUserByMobileToken(String mobileToken) {
        return repository.findByMobileToken(mobileToken);
    }
    // end::createUser[]
}
