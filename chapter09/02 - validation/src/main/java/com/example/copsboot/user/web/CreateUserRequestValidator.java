package com.example.copsboot.user.web;

import com.example.copsboot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//tag::class[]
public class CreateUserRequestValidator implements ConstraintValidator<ValidCreateUserRequest, CreateUserRequest> {

    private final UserService userService;

    @Autowired
    public CreateUserRequestValidator(UserService userService) { //<1>
        this.userService = userService;
    }

    @Override
    public void initialize(ValidCreateUserRequest constraintAnnotation) {

    }

    @Override
    public boolean isValid(CreateUserRequest userRequest, ConstraintValidatorContext context) {

        boolean result = true;

        if (userService.findUserByMobileToken(userRequest.mobileToken()).isPresent()) { //<2>
            context.buildConstraintViolationWithTemplate(
                    "There is already a user with the given mobile token.")
                   .addPropertyNode("mobileToken").addConstraintViolation(); //<3>

            result = false; //<4>
        }

        return result;
    }
}
//end::class[]
