package com.example.course_api.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para Student Domain Model (Arquitectura Hexagonal)")
class StudentTest {

    @Test
    @DisplayName("Debería crear un estudiante válido")
    void testCreateValidStudent() {
        Student student = new Student("Juan", "Pérez", "juan.perez@email.com");

        assertNotNull(student);
        assertEquals("Juan", student.getFirstName());
        assertEquals("Pérez", student.getLastName());
        assertEquals("juan.perez@email.com", student.getEmail());
    }

    @Test
    @DisplayName("Debería lanzar excepción si firstName está vacío")
    void testCreateStudent_EmptyFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("", "Pérez", "juan.perez@email.com");
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción si lastName está vacío")
    void testCreateStudent_EmptyLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Juan", "", "juan.perez@email.com");
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción si email está vacío")
    void testCreateStudent_EmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Juan", "Pérez", "");
        });
    }

    @Test
    @DisplayName("Debería lanzar excepción si email tiene formato inválido")
    void testCreateStudent_InvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("Juan", "Pérez", "invalid-email");
        });
    }

    @Test
    @DisplayName("Debería actualizar un estudiante correctamente")
    void testUpdateStudent() {
        Student student = new Student(1L, "Juan", "Pérez", "juan.perez@email.com");

        student.update("Pedro", "García", "pedro.garcia@email.com");

        assertEquals("Pedro", student.getFirstName());
        assertEquals("García", student.getLastName());
        assertEquals("pedro.garcia@email.com", student.getEmail());
    }

    @Test
    @DisplayName("Debería lanzar excepción al actualizar con datos inválidos")
    void testUpdateStudent_InvalidData() {
        Student student = new Student(1L, "Juan", "Pérez", "juan.perez@email.com");

        assertThrows(IllegalArgumentException.class, () -> {
            student.update("", "García", "pedro.garcia@email.com");
        });
    }

    @Test
    @DisplayName("Debería comparar estudiantes por ID y email")
    void testEquals() {
        Student student1 = new Student(1L, "Juan", "Pérez", "juan.perez@email.com");
        Student student2 = new Student(1L, "Pedro", "García", "juan.perez@email.com");
        Student student3 = new Student(2L, "Juan", "Pérez", "otro@email.com");

        assertEquals(student1, student2);
        assertNotEquals(student1, student3);
    }
}

