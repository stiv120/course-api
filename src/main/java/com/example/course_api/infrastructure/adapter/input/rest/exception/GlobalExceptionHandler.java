package com.example.course_api.infrastructure.adapter.input.rest.exception;

import com.example.course_api.domain.exception.DuplicateEmailException;
import com.example.course_api.domain.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        final Map<String, String> errors = buildValidationErrors(ex);
        return buildErrorResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        final Map<String, String> errors = buildSingleError("email", ex.getMessage());
        return buildErrorResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleStudentNotFoundException(StudentNotFoundException ex) {
        final Map<String, String> errors = buildSingleError("error", ex.getMessage());
        return buildErrorResponse(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateEmailException(DuplicateEmailException ex) {
        final Map<String, String> errors = buildSingleError("email", ex.getMessage());
        return buildErrorResponse(errors, HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> buildValidationErrors(final MethodArgumentNotValidException ex) {
        final Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

    private Map<String, String> buildSingleError(final String key, final String message) {
        final Map<String, String> errors = new HashMap<>();
        errors.put(key, message);
        return errors;
    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(final Map<String, String> errors, final HttpStatus status) {
        return new ResponseEntity<>(errors, status);
    }
}


