package com.example.copsboot.infrastructure.mvc;

import lombok.Value;

//tag::class[]
@Value
public class FieldErrorResponse {
    private String fieldName;
    private String errorMessage;
}
//end::class[]