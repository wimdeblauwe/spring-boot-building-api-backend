package com.example.copsboot.user.web;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//tag::class[]
@Target(ElementType.TYPE) // <1>
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CreateUserParametersValidator.class}) //<2>
public @interface ValidCreateUserParameters {
    String message() default "Invalid user";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
//end::class[]
