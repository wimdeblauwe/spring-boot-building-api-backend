package com.example.copsboot.user.web;

import com.example.copsboot.infrastructure.SpringProfiles;
import com.example.copsboot.user.AuthServerId;
import com.example.copsboot.user.User;
import com.example.copsboot.user.UserId;
import com.example.copsboot.user.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.example.copsboot.util.test.ConstraintViolationSetAssert.assertThat;
import static org.mockito.Mockito.when;

//tag::class[]
@SpringBootTest //<1>
@ActiveProfiles(SpringProfiles.REPOSITORY_TEST)
public class CreateUserRequestValidatorTest {

    @MockBean
    private UserService userService; //<2>
    @Autowired
    private ValidatorFactory factory; //<3>

    @Test
    public void invalidIfAlreadyUserWithGivenMobileToken() {

        String mobileToken = "abc123";
        when(userService.findUserByMobileToken(mobileToken))
                .thenReturn(Optional.of(new User(new UserId(UUID.randomUUID()),
                        "wim@example.com",
                        new AuthServerId(UUID.randomUUID()),
                        mobileToken)));

        Validator validator = factory.getValidator(); //<4>

        CreateUserRequest request = new CreateUserRequest(mobileToken);
        Set<ConstraintViolation<CreateUserRequest>> violationSet = validator.validate(request); //<5>
        assertThat(violationSet).hasViolationSize(2)
                .hasViolationOnPath("mobileToken"); //<6>
    }

    @Test
    public void validIfNoUserWithGivenMobileToken() {
        String mobileToken = "abc123";
        when(userService.findUserByMobileToken(mobileToken))
                .thenReturn(Optional.empty());

        Validator validator = factory.getValidator();

        CreateUserRequest request = new CreateUserRequest(mobileToken);
        Set<ConstraintViolation<CreateUserRequest>> violationSet = validator.validate(request);
        assertThat(violationSet).hasNoViolations();
    }
}
//end::class[]
