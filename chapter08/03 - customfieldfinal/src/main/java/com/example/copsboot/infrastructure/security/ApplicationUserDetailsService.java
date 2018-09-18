package com.example.copsboot.infrastructure.security;

import com.example.copsboot.user.User;
import com.example.copsboot.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service //<1>
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public ApplicationUserDetailsService(UserRepository userRepository) { // <2>
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmailIgnoreCase(username) //<3>
                                  .orElseThrow(() -> new UsernameNotFoundException( //<4>
                                                                                    String.format("User with email %s could not be found",
                                                                                           username)));
        return new ApplicationUserDetails(user); //<5>
    }
}
