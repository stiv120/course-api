package com.example.course_api.domain.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String email, String message) {
        super("Email " + email + " already exists. " + message);
    }
}


