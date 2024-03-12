package com.htw.gateway.error;


public class ErrorResponseException extends RuntimeException {

    public ErrorResponseException(String message) {
        super(message);
    }
}
