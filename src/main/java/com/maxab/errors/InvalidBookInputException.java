package com.maxab.errors;

public class InvalidBookInputException extends RuntimeException{
    
    public InvalidBookInputException(String message) {
        super(message);
    }

}
