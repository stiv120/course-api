package com.example.course_api.application.service;

import com.example.course_api.application.port.output.StudentRepositoryPort;
import com.example.course_api.domain.exception.DuplicateEmailException;
import com.example.course_api.domain.exception.StudentNotFoundException;
import com.example.course_api.domain.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for StudentService (Hexagonal Architecture)")
class StudentServiceTest {
    
    @Mock
    private StudentRepositoryPort studentRepositoryPort;
    
    @Mock
    private MessageSource messageSource;
    
    @InjectMocks
    private StudentService studentService;
    
    private Student testStudent;
    
    @BeforeEach
    void setUp() {
        testStudent = new Student(1L, "Juan", "Pérez", "juan.perez@email.com");
    }
    
    @Test
    @DisplayName("Should return all students")
    void testGetAllStudents() {
        List<Student> students = Arrays.asList(testStudent);
        when(studentRepositoryPort.findAll()).thenReturn(students);
        
        List<Student> result = studentService.getAllStudents();
        
        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getFirstName());
        verify(studentRepositoryPort, times(1)).findAll();
    }
    
    @Test
    @DisplayName("Should return a student by ID")
    void testGetStudentById() {
        when(studentRepositoryPort.findById(1L)).thenReturn(Optional.of(testStudent));
        
        Optional<Student> result = studentService.getStudentById(1L);
        
        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getFirstName());
        verify(studentRepositoryPort, times(1)).findById(1L);
    }
    
    @Test
    @DisplayName("Should return empty Optional if student does not exist")
    void testGetStudentById_NotFound() {
        when(studentRepositoryPort.findById(999L)).thenReturn(Optional.empty());
        
        Optional<Student> result = studentService.getStudentById(999L);
        
        assertFalse(result.isPresent());
        verify(studentRepositoryPort, times(1)).findById(999L);
    }
    
    @Test
    @DisplayName("Should create a student successfully")
    void testCreateStudent() {
        when(studentRepositoryPort.existsByEmail(anyString())).thenReturn(false);
        when(studentRepositoryPort.save(any(Student.class))).thenReturn(testStudent);
        
        Student result = studentService.createStudent(testStudent);
        
        assertNotNull(result);
        assertEquals("Juan", result.getFirstName());
        verify(studentRepositoryPort, times(1)).existsByEmail(testStudent.getEmail());
        verify(studentRepositoryPort, times(1)).save(testStudent);
    }
    
    @Test
    @DisplayName("Should throw DuplicateEmailException if email already exists")
    @SuppressWarnings("null") // MessageSource.getMessage and LocaleContextHolder.getLocale are guaranteed non-null
    void testCreateStudent_DuplicateEmail() {
        when(studentRepositoryPort.existsByEmail(testStudent.getEmail())).thenReturn(true);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Email already exists");
        
        assertThrows(DuplicateEmailException.class, () -> {
            studentService.createStudent(testStudent);
        });
        
        verify(studentRepositoryPort, times(1)).existsByEmail(testStudent.getEmail());
        verify(studentRepositoryPort, never()).save(any(Student.class));
    }
    
    @Test
    @DisplayName("Should update an existing student")
    void testUpdateStudent() {
        Student updatedStudent = new Student("Pedro", "García", "pedro.garcia@email.com");
        
        when(studentRepositoryPort.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepositoryPort.existsByEmail(anyString())).thenReturn(false);
        when(studentRepositoryPort.save(any(Student.class))).thenReturn(testStudent);
        
        Student result = studentService.updateStudent(1L, updatedStudent);
        
        assertNotNull(result);
        verify(studentRepositoryPort, times(1)).findById(1L);
        verify(studentRepositoryPort, times(1)).save(any(Student.class));
    }
    
    @Test
    @DisplayName("Should throw StudentNotFoundException when updating non-existent student")
    @SuppressWarnings("null") // MessageSource.getMessage and LocaleContextHolder.getLocale are guaranteed non-null
    void testUpdateStudent_NotFound() {
        Student updatedStudent = new Student("Pedro", "García", "pedro.garcia@email.com");
        
        when(studentRepositoryPort.findById(999L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Student not found");
        
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.updateStudent(999L, updatedStudent);
        });
        
        verify(studentRepositoryPort, times(1)).findById(999L);
        verify(studentRepositoryPort, never()).save(any(Student.class));
    }
    
    @Test
    @DisplayName("Should delete a student")
    void testDeleteStudent() {
        when(studentRepositoryPort.findById(1L)).thenReturn(Optional.of(testStudent));
        doNothing().when(studentRepositoryPort).deleteById(1L);
        
        studentService.deleteStudent(1L);
        
        verify(studentRepositoryPort, times(1)).findById(1L);
        verify(studentRepositoryPort, times(1)).deleteById(1L);
    }
    
    @Test
    @DisplayName("Should throw StudentNotFoundException when deleting non-existent student")
    @SuppressWarnings("null") // MessageSource.getMessage and LocaleContextHolder.getLocale are guaranteed non-null
    void testDeleteStudent_NotFound() {
        when(studentRepositoryPort.findById(999L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Student not found");
        
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.deleteStudent(999L);
        });
        
        verify(studentRepositoryPort, times(1)).findById(999L);
        verify(studentRepositoryPort, never()).deleteById(anyLong());
    }
}


