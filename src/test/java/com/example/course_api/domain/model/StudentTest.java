package com.example.course_api.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Student Domain Model (Hexagonal Architecture)")
class StudentTest {

    @Test
    @DisplayName("Should create a valid student")
    void testCreateValidStudent() {
        Student student = new Student("Juan", "Pérez", "juan.perez@email.com");

        assertNotNull(student);
        assertEquals("Juan", student.getFirstName());
        assertEquals("Pérez", student.getLastName());
        assertEquals("juan.perez@email.com", student.getEmail());
    }

    @Test
    @DisplayName("Should throw exception if firstName is empty")
    void testCreateStudent_EmptyFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("", "Pérez", "juan.perez@email.com");
        });
    }

    @Test
    @DisplayName("Should throw exception if lastName is empty")
    void testCreateStudent_EmptyLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Juan", "", "juan.perez@email.com");
        });
    }

    @Test
    @DisplayName("Should throw exception if email is empty")
    void testCreateStudent_EmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Juan", "Pérez", "");
        });
    }

    @Test
    @DisplayName("Should throw exception if email has invalid format")
    void testCreateStudent_InvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Juan", "Pérez", "invalid-email");
        });
    }

    @Test
    @DisplayName("Should update a student correctly")
    void testUpdateStudent() {
        Student student = new Student(1L, "Juan", "Pérez", "juan.perez@email.com");

        student.update("Pedro", "García", "pedro.garcia@email.com");

        assertEquals("Pedro", student.getFirstName());
        assertEquals("García", student.getLastName());
        assertEquals("pedro.garcia@email.com", student.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when updating with invalid data")
    void testUpdateStudent_InvalidData() {
        Student student = new Student(1L, "Juan", "Pérez", "juan.perez@email.com");

        assertThrows(IllegalArgumentException.class, () -> {
            student.update("", "García", "pedro.garcia@email.com");
        });
    }

    @Test
    @DisplayName("Should compare students by ID and email")
    void testEquals() {
        Student student1 = new Student(1L, "Juan", "Pérez", "juan.perez@email.com");
        Student student2 = new Student(1L, "Pedro", "García", "juan.perez@email.com");
        Student student3 = new Student(2L, "Juan", "Pérez", "otro@email.com");

        assertEquals(student1, student2);
        assertNotEquals(student1, student3);
    }
}
