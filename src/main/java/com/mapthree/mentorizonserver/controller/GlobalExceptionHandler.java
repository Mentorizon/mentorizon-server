package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.exception.EmailInUseException;
import com.mapthree.mentorizonserver.exception.SignupInformationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(SignupInformationException.class)
    public ResponseEntity<String> handleSignupInformationException(SignupInformationException e) {
        e.printStackTrace();  // TODO: research logging possibilities
        return ResponseEntity.badRequest().body("Missing or invalid signup information.");
    }

    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<String> handleEmailInUseException(EmailInUseException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body("User with this email already exists.");
    }
}
