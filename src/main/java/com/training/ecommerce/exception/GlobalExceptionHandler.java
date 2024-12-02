package com.training.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
          Map<String, Object> body = new HashMap<>();
          body.put("timestamp", LocalDateTime.now());
          body.put("error", "Resource Not Found");
          body.put("message", ex.getMessage());
          body.put("details", "Pleas check the provided resource ID and try again");
          body.put("status", HttpStatus.NOT_FOUND.value());

          return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(EmailAlreadyRegisteredException ex) {
          Map<String, Object> body = new HashMap<>();
          body.put("timestamp", LocalDateTime.now());
          body.put("message",ex.getMessage());
          body.put("details", "Please try another email");
          body.put("status", HttpStatus.BAD_REQUEST);
          return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
