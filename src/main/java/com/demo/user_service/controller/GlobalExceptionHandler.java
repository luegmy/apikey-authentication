package com.demo.user_service.controller;

import com.demo.user_service.exception.UserExistsException;
import com.demo.user_service.exception.InvalidRolException;
import com.demo.user_service.exception.NotFoundUsernameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<Object> handleValidationExceptions(UserExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
    @ExceptionHandler(InvalidRolException.class)
    public ResponseEntity<Object> handleValidationExceptions(InvalidRolException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
    @ExceptionHandler(NotFoundUsernameException.class)
    public ResponseEntity<Object> handleValidationExceptions(NotFoundUsernameException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleValidationExceptions(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }
}
