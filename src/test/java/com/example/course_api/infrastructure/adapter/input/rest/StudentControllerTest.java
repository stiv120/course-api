package com.example.course_api.infrastructure.adapter.input.rest;

import com.example.course_api.application.port.input.StudentUseCase;
import com.example.course_api.domain.model.Student;
import com.example.course_api.infrastructure.adapter.input.rest.dto.StudentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.course_api.infrastructure.adapter.input.rest.exception.GlobalExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@Import(GlobalExceptionHandler.class)
@DisplayName("Tests for StudentController (Hexagonal Architecture)")
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentUseCase studentUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student(1L, "Juan", "Pérez", "juan.perez@email.com");
    }

    @Test
    @DisplayName("GET /api/v1/students - Should return list of students")
    @SuppressWarnings("null") // MediaType.APPLICATION_JSON is a Spring constant, guaranteed non-null
    void testGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(testStudent);
        when(studentUseCase.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].studentId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Juan"))
                .andExpect(jsonPath("$[0].lastName").value("Pérez"))
                .andExpect(jsonPath("$[0].email").value("juan.perez@email.com"));

        verify(studentUseCase, times(1)).getAllStudents();
    }

    @Test
    @DisplayName("GET /api/v1/students/{id} - Should return a student by ID")
    @SuppressWarnings("null") // MediaType.APPLICATION_JSON is a Spring constant, guaranteed non-null
    void testGetStudentById() throws Exception {
        when(studentUseCase.getStudentById(1L)).thenReturn(Optional.of(testStudent));

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.firstName").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@email.com"));

        verify(studentUseCase, times(1)).getStudentById(1L);
    }

    @Test
    @DisplayName("GET /api/v1/students/{id} - Should return 404 if student does not exist")
    void testGetStudentById_NotFound() throws Exception {
        when(studentUseCase.getStudentById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/students/999"))
                .andExpect(status().isNotFound());

        verify(studentUseCase, times(1)).getStudentById(999L);
    }

    @Test
    @DisplayName("POST /api/v1/students - Should create a student successfully")
    @SuppressWarnings("null") // MediaType and ObjectMapper.writeValueAsString are guaranteed non-null
    void testCreateStudent() throws Exception {
        StudentRequest request = new StudentRequest("María", "González", "maria.gonzalez@email.com");
        Student created = new Student(1L, "María", "González", "maria.gonzalez@email.com");

        when(studentUseCase.createStudent(any(Student.class))).thenReturn(created);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.firstName").value("María"));

        verify(studentUseCase, times(1)).createStudent(any(Student.class));
    }

    @Test
    @DisplayName("POST /api/v1/students - Should return 400 if data is invalid")
    @SuppressWarnings("null") // MediaType and ObjectMapper.writeValueAsString are guaranteed non-null
    void testCreateStudent_InvalidData() throws Exception {
        StudentRequest invalidRequest = new StudentRequest("", "", "invalid-email");

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(studentUseCase, never()).createStudent(any(Student.class));
    }

    @Test
    @DisplayName("PUT /api/v1/students/{id} - Should update a student successfully")
    @SuppressWarnings("null") // MediaType and ObjectMapper.writeValueAsString are guaranteed non-null
    void testUpdateStudent() throws Exception {
        StudentRequest request = new StudentRequest("Pedro", "García", "pedro.garcia@email.com");
        Student updated = new Student(1L, "Pedro", "García", "pedro.garcia@email.com");

        when(studentUseCase.updateStudent(eq(1L), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Pedro"));

        verify(studentUseCase, times(1)).updateStudent(eq(1L), any(Student.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/students/{id} - Should delete a student")
    void testDeleteStudent() throws Exception {
        doNothing().when(studentUseCase).deleteStudent(1L);

        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isNoContent());

        verify(studentUseCase, times(1)).deleteStudent(1L);
    }
}
