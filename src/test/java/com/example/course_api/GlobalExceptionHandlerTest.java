package com.example.course_api;

import com.example.course_api.controller.StudentController;
import com.example.course_api.entity.Student;
import com.example.course_api.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@DisplayName("Tests para GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setFirstName("Juan");
        testStudent.setLastName("Pérez");
        testStudent.setEmail("juan.perez@email.com");
    }

    @Test
    @DisplayName("Debería manejar MethodArgumentNotValidException - campos vacíos")
    void testHandleValidationException_EmptyFields() throws Exception {
        Student invalidStudent = new Student();
        invalidStudent.setFirstName(""); // Campo vacío
        invalidStudent.setLastName(""); // Campo vacío
        invalidStudent.setEmail(""); // Campo vacío

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.email").exists());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    @DisplayName("Debería manejar MethodArgumentNotValidException - email inválido")
    void testHandleValidationException_InvalidEmail() throws Exception {
        Student invalidStudent = new Student();
        invalidStudent.setFirstName("Juan");
        invalidStudent.setLastName("Pérez");
        invalidStudent.setEmail("invalid-email"); // Email inválido

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").exists());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    @DisplayName("Debería manejar IllegalArgumentException - email duplicado")
    void testHandleIllegalArgumentException_DuplicateEmail() throws Exception {
        when(studentService.createStudent(any(Student.class)))
                .thenThrow(new IllegalArgumentException("Email ya existe"));

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStudent)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email ya existe"));

        verify(studentService, times(1)).createStudent(any(Student.class));
    }

    @Test
    @DisplayName("Debería manejar IllegalArgumentException - estudiante no encontrado")
    void testHandleIllegalArgumentException_StudentNotFound() throws Exception {
        when(studentService.updateStudent(anyLong(), any(Student.class)))
                .thenThrow(new IllegalArgumentException("Estudiante no encontrado"));

        mockMvc.perform(put("/api/v1/students/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStudent)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Estudiante no encontrado"));

        verify(studentService, times(1)).updateStudent(anyLong(), any(Student.class));
    }

    @Test
    @DisplayName("Debería manejar validación en PUT request")
    void testHandleValidationException_PutRequest() throws Exception {
        Student invalidStudent = new Student();
        invalidStudent.setFirstName(""); // Campo vacío
        invalidStudent.setEmail("invalid-email");

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.email").exists());

        verify(studentService, never()).updateStudent(anyLong(), any(Student.class));
    }

    @Test
    @DisplayName("Debería retornar múltiples errores de validación")
    void testHandleValidationException_MultipleErrors() throws Exception {
        Student invalidStudent = new Student();
        invalidStudent.setFirstName(null);
        invalidStudent.setLastName(null);
        invalidStudent.setEmail("not-an-email");

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.email").exists());
    }
}

