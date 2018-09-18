package com.example.copsboot.user.web;

import com.example.copsboot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//tag::class[]
public class CreateUserParametersValidator implements ConstraintValidator<ValidCreateUserParameters, CreateOfficerParameters> {

    private final UserService userService;

    @Autowired
    public CreateUserParametersValidator(UserService userService) { //<1>
        this.userService = userService;
    }

    @Override
    public void initialize(ValidCreateUserParameters constraintAnnotation) {

    }

    @Override
    public boolean isValid(CreateOfficerParameters userParameters, ConstraintValidatorContext context) {

        boolean result = true;

        if (userService.findUserByEmail(userParameters.getEmail()).isPresent()) { //<2>
            context.buildConstraintViolationWithTemplate(
                    "There is already a user with the given email address.")
                   .addPropertyNode("email").addConstraintViolation(); //<3>

            result = false; //<4>
        }

        return result;
    }
}
//end::class[]
