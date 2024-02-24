package com.example.copsboot.report.web;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//tag::class[]
public class CreateReportRequestValidator implements ConstraintValidator<ValidCreateReportRequest, CreateReportRequest> { //<1>

    @Override
    public void initialize(ValidCreateReportRequest constraintAnnotation) {
    }

    @Override
    public boolean isValid(CreateReportRequest value, ConstraintValidatorContext context) {
        boolean result = true;
        if (value.trafficIncident() && value.numberOfInvolvedCars() <= 0) { //<2>
            result = false;
        }
        return result;
    }
} //end::class[]
