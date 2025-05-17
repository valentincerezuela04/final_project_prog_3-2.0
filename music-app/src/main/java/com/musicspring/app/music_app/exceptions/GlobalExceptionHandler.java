package com.musicspring.app.music_app.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDetails.from(ex.getMessage(), request.getDescription(false)));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDetails.from(ex.getMessage(), request.getDescription(false)));
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorDetails> handleHttpClientError(HttpClientErrorException ex, WebRequest request) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ErrorDetails.from("Spotify API error: " + ex.getMessage(), request.getDescription(false)));
    }
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorDetails> handleResourceAccessException(ResourceAccessException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ErrorDetails.from("Could not connect to Spotify service", request.getDescription(false)));
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDetails> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDetails.from(ex.getMessage(), request.getDescription(false)));
    }


}
