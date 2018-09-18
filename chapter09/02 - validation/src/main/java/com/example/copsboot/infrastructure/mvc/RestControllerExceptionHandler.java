package com.example.copsboot.infrastructure.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//tag::class[]
@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<FieldErrorResponse>> handle(MethodArgumentNotValidException exception) {
        return error(exception.getBindingResult()
                              .getFieldErrors()
                              .stream()
                              .map(fieldError -> new FieldErrorResponse(fieldError.getField(),
                                                                        fieldError.getDefaultMessage()))
                              .collect(Collectors.toList()));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, List<FieldErrorResponse>> handle(BindException exception) {
        return error(exception.getBindingResult()
                              .getFieldErrors()
                              .stream()
                              .map(fieldError -> new FieldErrorResponse(fieldError.getField(),
                                                                        fieldError.getDefaultMessage()))
                              .collect(Collectors.toList()));
    }

    //tag::multipart-exception[]
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity handleMultipartException(MultipartException e, Model model) {
        model.addAttribute("exception", e);
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
    //end::multipart-exception[]

    private Map<String, List<FieldErrorResponse>> error(List<FieldErrorResponse> errors) {
        return Collections.singletonMap("errors", errors);
    }
}
//end::class[]
