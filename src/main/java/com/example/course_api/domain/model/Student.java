package com.example.course_api.domain.model;

import java.util.Objects;

public class Student {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;

    public Student() {
    }

    public Student(Long studentId, String firstName, String lastName, String email) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        validate();
    }

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        validate();
    }

    private void validate() {
        validateFirstName();
        validateLastName();
        validateEmail();
    }

    private void validateFirstName() {
        if (isNullOrEmpty(firstName)) {
            throw new IllegalArgumentException("First name is required");
        }
    }

    private void validateLastName() {
        if (isNullOrEmpty(lastName)) {
            throw new IllegalArgumentException("Last name is required");
        }
    }

    private void validateEmail() {
        if (isNullOrEmpty(email)) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!isValidEmailFormat(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidEmailFormat(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public void update(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        validate();
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        validate();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        validate();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        validate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) &&
               Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, email);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


