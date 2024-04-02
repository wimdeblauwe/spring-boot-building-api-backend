package com.example.copsboot.report.web;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Test;

import java.time.Instant;
import java.util.Set;

import static com.example.copsboot.util.test.ConstraintViolationSetAssert.assertThat;

public class ReportDescriptionValidatorTest {

    //tag::invalid[]
    @Test
    public void givenEmptyString_notValid() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) { //<1>
            Validator validator = factory.getValidator(); //<2>

            CreateReportRequest parameters = new CreateReportRequest(Instant.now(), "", false, 0);
            Set<ConstraintViolation<CreateReportRequest>> violationSet = validator.validate(parameters); //<3>
            assertThat(violationSet).hasViolationOnPath("description"); //<4>
        }
    }
    //end::invalid[]

    //tag::valid[]
    @Test
    public void givenSuspectWordPresent_valid() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            CreateReportRequest parameters = new CreateReportRequest(Instant.now(),
                    "The suspect was wearing a black hat.", false, 0);
            Set<ConstraintViolation<CreateReportRequest>> violationSet = validator.validate(parameters);
            assertThat(violationSet).hasNoViolations();
        }
    }
    //end::valid[]
}
