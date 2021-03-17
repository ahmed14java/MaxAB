package com.maxab.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Date Must Match With yyyy-MM-dd ")
public class InvalidDateFormat extends Exception{

    public InvalidDateFormat(String message) {
        super(message);
    }
}
