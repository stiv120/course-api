package com.example.course_api.controller;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@DisplayName("Tests para StudentController")
class StudentControllerTest {

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
        testStudent.setStudentId(1L);
        testStudent.setFirstName("Juan");
        testStudent.setLastName("Pérez");
        testStudent.setEmail("juan.perez@email.com");
    }

    @Test
    @DisplayName("GET /api/v1/students - Debería retornar lista de estudiantes")
    void testGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(testStudent);
        when(studentService.getStudents()).thenReturn(students);

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].studentId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Juan"))
                .andExpect(jsonPath("$[0].lastName").value("Pérez"))
                .andExpect(jsonPath("$[0].email").value("juan.perez@email.com"));

        verify(studentService, times(1)).getStudents();
    }

    @Test
    @DisplayName("GET /api/v1/students/{id} - Debería retornar un estudiante por ID")
    void testGetStudentById() throws Exception {
        when(studentService.getStudent(1L)).thenReturn(Optional.of(testStudent));

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.firstName").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@email.com"));

        verify(studentService, times(1)).getStudent(1L);
    }

    @Test
    @DisplayName("GET /api/v1/students/{id} - Debería retornar 200 aunque el estudiante no exista")
    void testGetStudentById_NotFound() throws Exception {
        when(studentService.getStudent(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/students/999"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).getStudent(999L);
    }

    @Test
    @DisplayName("POST /api/v1/students - Debería crear un estudiante exitosamente")
    void testCreateStudent() throws Exception {
        Student newStudent = new Student();
        newStudent.setFirstName("María");
        newStudent.setLastName("González");
        newStudent.setEmail("maria.gonzalez@email.com");

        when(studentService.createStudent(any(Student.class))).thenReturn(newStudent);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent)))
                .andExpect(status().isOk());

        verify(studentService, times(1)).createStudent(any(Student.class));
    }

    @Test
    @DisplayName("POST /api/v1/students - Debería retornar 400 si los datos son inválidos")
    void testCreateStudent_InvalidData() throws Exception {
        Student invalidStudent = new Student();
        invalidStudent.setFirstName(""); // Campo vacío
        invalidStudent.setEmail("invalid-email"); // Email inválido

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    @DisplayName("PUT /api/v1/students/{id} - Debería actualizar un estudiante exitosamente")
    void testUpdateStudent() throws Exception {
        Student updatedStudent = new Student();
        updatedStudent.setFirstName("Pedro");
        updatedStudent.setLastName("García");
        updatedStudent.setEmail("pedro.garcia@email.com");

        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk());

        verify(studentService, times(1)).updateStudent(eq(1L), any(Student.class));
    }

    @Test
    @DisplayName("PUT /api/v1/students/{id} - Debería retornar 400 si los datos son inválidos")
    void testUpdateStudent_InvalidData() throws Exception {
        Student invalidStudent = new Student();
        invalidStudent.setFirstName(""); // Campo vacío

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).updateStudent(anyLong(), any(Student.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/students/{id} - Debería eliminar un estudiante")
    void testDeleteStudent() throws Exception {
        doNothing().when(studentService).delete(1L);

        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("GET /api/v1/students - Debería retornar lista vacía cuando no hay estudiantes")
    void testGetAllStudents_EmptyList() throws Exception {
        when(studentService.getStudents()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(studentService, times(1)).getStudents();
    }
}

