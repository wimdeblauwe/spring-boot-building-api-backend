package com.example.copsboot.report.web;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//tag::class[]
@Target(ElementType.TYPE) //<1>
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CreateReportParametersValidator.class}) //<2>
public @interface ValidCreateReportParameters {
    String message() default "Invalid report";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}//end::class[]