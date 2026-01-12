package com.routhtech.restdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAgeInvalidException extends RuntimeException {
    public UserAgeInvalidException(Integer age) {
        super("Invalid User Age: " + age + ". Age should be >= 0");
    }
}
