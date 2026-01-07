package com.example.course_api.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para Student Entity")
class StudentTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería crear un estudiante válido")
    void testValidStudent() {
        Student student = new Student();
        student.setFirstName("Juan");
        student.setLastName("Pérez");
        student.setEmail("juan.perez@email.com");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debería fallar validación si firstName está vacío")
    void testInvalidStudent_EmptyFirstName() {
        Student student = new Student();
        student.setFirstName("");
        student.setLastName("Pérez");
        student.setEmail("juan.perez@email.com");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    @DisplayName("Debería fallar validación si firstName es null")
    void testInvalidStudent_NullFirstName() {
        Student student = new Student();
        student.setFirstName(null);
        student.setLastName("Pérez");
        student.setEmail("juan.perez@email.com");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
    }

    @Test
    @DisplayName("Debería fallar validación si lastName está vacío")
    void testInvalidStudent_EmptyLastName() {
        Student student = new Student();
        student.setFirstName("Juan");
        student.setLastName("");
        student.setEmail("juan.perez@email.com");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
    }

    @Test
    @DisplayName("Debería fallar validación si email está vacío")
    void testInvalidStudent_EmptyEmail() {
        Student student = new Student();
        student.setFirstName("Juan");
        student.setLastName("Pérez");
        student.setEmail("");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Debería fallar validación si email tiene formato inválido")
    void testInvalidStudent_InvalidEmailFormat() {
        Student student = new Student();
        student.setFirstName("Juan");
        student.setLastName("Pérez");
        student.setEmail("invalid-email");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    @DisplayName("Debería aceptar email con formato válido")
    void testValidStudent_ValidEmailFormats() {
        String[] validEmails = {
                "test@email.com",
                "user.name@domain.co.uk",
                "firstname+lastname@example.com",
                "email@123.123.123.123"
        };

        for (String email : validEmails) {
            Student student = new Student();
            student.setFirstName("Juan");
            student.setLastName("Pérez");
            student.setEmail(email);

            Set<ConstraintViolation<Student>> violations = validator.validate(student);

            assertTrue(violations.isEmpty(), 
                    "Email " + email + " debería ser válido");
        }
    }

    @Test
    @DisplayName("Debería tener getters y setters funcionando correctamente")
    void testGettersAndSetters() {
        Student student = new Student();
        student.setStudentId(1L);
        student.setFirstName("Juan");
        student.setLastName("Pérez");
        student.setEmail("juan.perez@email.com");

        assertEquals(1L, student.getStudentId());
        assertEquals("Juan", student.getFirstName());
        assertEquals("Pérez", student.getLastName());
        assertEquals("juan.perez@email.com", student.getEmail());
    }
}

