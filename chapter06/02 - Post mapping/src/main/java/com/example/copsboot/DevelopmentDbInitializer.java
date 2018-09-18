package com.example.copsboot;

import com.example.copsboot.infrastructure.SpringProfiles;
import com.example.copsboot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component //<1>
@Profile(SpringProfiles.DEV) //<2>
public class DevelopmentDbInitializer implements ApplicationRunner {

    private final UserService userService;

    @Autowired
    public DevelopmentDbInitializer(UserService userService) { //<3>
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) { //<4>
        createTestUsers();
    }

    private void createTestUsers() {
        userService.createOfficer("officer@example.com", "officer"); //<5>
    }
}
