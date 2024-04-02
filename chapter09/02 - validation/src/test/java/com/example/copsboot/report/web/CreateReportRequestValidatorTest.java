package com.example.copsboot.report.web;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.Instant;
import java.util.Set;

import static com.example.copsboot.util.test.ConstraintViolationSetAssert.assertThat;

public class CreateReportRequestValidatorTest {
    //tag::invalid[]
    @Test
    public void givenTrafficIndicentButInvolvedCarsZero_invalid() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            CreateReportRequest parameters = new CreateReportRequest(Instant.now(),
                    "The suspect was wearing a black hat",
                    true,
                    0,
                    createImage());
            Set<ConstraintViolation<CreateReportRequest>> violationSet = validator.validate(parameters);
            assertThat(violationSet).hasViolationOnPath("");
        }
    }
    //end::invalid[]

    //tag::valid[]
    @Test
    public void givenTrafficIndicent_involvedCarsMustBePositive() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            CreateReportRequest parameters = new CreateReportRequest(Instant.now(),
                    "The suspect was wearing a black hat.",
                    true,
                    2,
                    createImage());
            Set<ConstraintViolation<CreateReportRequest>> violationSet = validator.validate(parameters);
            assertThat(violationSet).hasNoViolations();
        }
    }
    //end::valid[]

    //tag::valid-no-cars[]
    @Test
    public void givenNoTrafficIndicent_involvedCarsDoesNotMatter() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            CreateReportRequest parameters = new CreateReportRequest(Instant.now(),
                    "The suspect was wearing a black hat.",
                    false,
                    0,
                    createImage());
            Set<ConstraintViolation<CreateReportRequest>> violationSet = validator.validate(parameters);
            assertThat(violationSet).hasNoViolations();
        }
    }
    //end::valid-no-cars[]

    @NotNull
    private static MockMultipartFile createImage() {
        return new MockMultipartFile("image", "picture.png", MediaType.IMAGE_PNG_VALUE, new byte[]{1, 2, 3});
    }

}
