package com.example.copsboot.report.web;


import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.ZonedDateTime;
import java.util.Set;

import static com.example.copsboot.util.test.ConstraintViolationSetAssert.assertThat;

public class CreateReportParametersValidatorTest {
    //tag::invalid[]
    @Test
    public void givenTrafficIndicentButInvolvedCarsZero_invalid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(),
                                                                       "The suspect was wearing a black hat",
                                                                       true,
                                                                       0);
        Set<ConstraintViolation<CreateReportParameters>> violationSet = validator.validate(parameters);
        assertThat(violationSet).hasViolationOnPath("");
    }
    //end::invalid[]

    //tag::valid[]
    @Test
    public void givenTrafficIndicent_involvedCarsMustBePositive() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(),
                                                                       "The suspect was wearing a black hat.",
                                                                       true,
                                                                       2);
        Set<ConstraintViolation<CreateReportParameters>> violationSet = validator.validate(parameters);
        assertThat(violationSet).hasNoViolations();
    }
    //end::valid[]

    //tag::valid-no-cars[]
    @Test
    public void givenNoTrafficIndicent_involvedCarsDoesNotMatter() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(),
                                                                       "The suspect was wearing a black hat.",
                                                                       false,
                                                                       0);
        Set<ConstraintViolation<CreateReportParameters>> violationSet = validator.validate(parameters);
        assertThat(violationSet).hasNoViolations();
    }
    //end::valid-no-cars[]
}