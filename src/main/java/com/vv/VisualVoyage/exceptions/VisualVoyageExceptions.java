package com.vv.VisualVoyage.exceptions;

import org.springframework.http.HttpStatus;

public class VisualVoyageExceptions extends RuntimeException {
    private HttpStatus _status;
    public VisualVoyageExceptions(String message, HttpStatus status) {
        super(message);
        _status = status;
    }
}
