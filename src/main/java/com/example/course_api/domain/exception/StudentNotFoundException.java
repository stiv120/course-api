package com.example.course_api.domain.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(Long studentId) {
        super("Student with id " + studentId + " not found");
    }
}


