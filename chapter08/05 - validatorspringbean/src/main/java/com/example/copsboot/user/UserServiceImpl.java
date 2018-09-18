package com.example.copsboot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createOfficer(String email, String password) {
        User user = User.createOfficer(repository.nextId(), email, passwordEncoder.encode(password));
        return repository.save(user);
    }

    @Override
    public Optional<User> getUser(UserId userId) {
        return repository.findById(userId);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return repository.findByEmailIgnoreCase(email);
    }
}
