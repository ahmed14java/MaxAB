package com.maxab.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find book with id.")
public class BookNotFoundException extends Exception{

    public BookNotFoundException(String message) {
        super(message);
    }
}
