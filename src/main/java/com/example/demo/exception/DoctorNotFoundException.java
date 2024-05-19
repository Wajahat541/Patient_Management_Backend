package com.example.demo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DoctorNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DoctorNotFoundException(String message) {
        super(message);
    }
}
