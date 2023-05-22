package com.scheduler.project.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(MethodArgumentNotValidException e) {
        String responseHeader = "ValidationError";
        List<FieldError> errors = e.getBindingResult().getFieldErrors();

        Map<String, List<String>> responseData = new HashMap<>();
        List<String> messages = errors.stream().map(FieldError::getDefaultMessage).toList();
        responseData.put(responseHeader, messages);

        return ResponseEntity.badRequest().body(responseData);
    }
}
