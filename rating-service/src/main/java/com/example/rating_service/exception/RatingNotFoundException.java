package com.example.rating_service.exception;

public class RatingNotFoundException extends RuntimeException {
    
    public RatingNotFoundException(String message) {
        super(message);
    }
    
    public RatingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

