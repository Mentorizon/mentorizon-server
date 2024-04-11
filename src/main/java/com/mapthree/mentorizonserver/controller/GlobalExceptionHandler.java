package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.exception.EmailInUseException;
import com.mapthree.mentorizonserver.exception.SignupInformationException;
import com.mapthree.mentorizonserver.exception.UserNotFoundException;
import com.mapthree.mentorizonserver.exception.mentorshipapplication.AlreadyMentoredException;
import com.mapthree.mentorizonserver.exception.mentorshipapplication.ApplicationPendingException;
import com.mapthree.mentorizonserver.exception.mentorshipapplication.MentorshipApplicationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        String errorMessage;
        String exceptionName = mostSpecificCause.getClass().getName();
        String message = mostSpecificCause.getMessage();
        errorMessage = message != null ? message : exceptionName;

        if (errorMessage.contains("UUID")) {
            errorMessage = "Invalid UUID format. Please provide a valid UUID.";
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(ApplicationPendingException.class)
    public ResponseEntity<String> handleApplicationPendingException(ApplicationPendingException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyMentoredException.class)
    public ResponseEntity<String> handleAlreadyMentoredException(AlreadyMentoredException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MentorshipApplicationNotFoundException.class)
    public ResponseEntity<String> handleMentorshipApplicationNotFoundException(MentorshipApplicationNotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
