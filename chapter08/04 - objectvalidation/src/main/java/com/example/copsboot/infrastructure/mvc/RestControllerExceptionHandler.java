package com.example.copsboot.infrastructure.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//tag::class[]
@ControllerAdvice //<1>
public class RestControllerExceptionHandler {

    @ExceptionHandler //<2>
    @ResponseBody //<3>
    @ResponseStatus(HttpStatus.BAD_REQUEST) //<4>
    public Map<String, List<FieldErrorResponse>> handle(MethodArgumentNotValidException exception) { //<5>
        return error(exception.getBindingResult()
                              .getFieldErrors()
                              .stream()
                              .map(fieldError -> new FieldErrorResponse(fieldError.getField(), //<6>
                                                                        fieldError.getDefaultMessage()))
                              .collect(Collectors.toList()));
    }

    private Map<String, List<FieldErrorResponse>> error(List<FieldErrorResponse> errors) {
        return Collections.singletonMap("errors", errors);
    }
}
//end::class[]
