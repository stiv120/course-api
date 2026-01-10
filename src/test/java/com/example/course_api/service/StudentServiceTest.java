package com.example.course_api.service;

import com.example.course_api.entity.Student;
import com.example.course_api.repository.StudentRepository;
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
@DisplayName("Tests for StudentService")
class StudentServiceTest {
    
    @Mock
    private StudentRepository studentRepository;
    
    @Mock
    private MessageSource messageSource;
    
    @InjectMocks
    private StudentService studentService;
    
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
    @DisplayName("Should return all students")
    void testGetStudents() {
        List<Student> students = Arrays.asList(testStudent);
        when(studentRepository.findAll()).thenReturn(students);
        
        List<Student> result = studentService.getStudents();
        
        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getFirstName());
        verify(studentRepository, times(1)).findAll();
    }
    
    @Test
    @DisplayName("Should return a student by ID")
    void testGetStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        
        Optional<Student> result = studentService.getStudent(1L);
        
        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getFirstName());
        verify(studentRepository, times(1)).findById(1L);
    }
    
    @Test
    @DisplayName("Should return empty Optional if student does not exist")
    void testGetStudent_NotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());
        
        Optional<Student> result = studentService.getStudent(999L);
        
        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(999L);
    }
    
    @Test
    @DisplayName("Should create a student successfully")
    @SuppressWarnings("null") // Spring Data JPA save() contract guarantees non-null return
    void testCreateStudent() {
        when(studentRepository.existsByEmail(anyString())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);
        
        Student result = studentService.createStudent(testStudent);
        
        assertNotNull(result);
        assertEquals("Juan", result.getFirstName());
        verify(studentRepository, times(1)).existsByEmail(testStudent.getEmail());
        verify(studentRepository, times(1)).save(testStudent);
    }
    
    @Test
    @DisplayName("Should throw exception if email already exists")
    @SuppressWarnings("null") // MessageSource.getMessage and LocaleContextHolder.getLocale are guaranteed non-null
    void testCreateStudent_DuplicateEmail() {
        when(studentRepository.existsByEmail(testStudent.getEmail())).thenReturn(true);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Email already exists");
        
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.createStudent(testStudent);
        });
        
        verify(studentRepository, times(1)).existsByEmail(testStudent.getEmail());
        verify(studentRepository, never()).save(any(Student.class));
    }
    
    @Test
    @DisplayName("Should update an existing student")
    @SuppressWarnings("null") // Spring Data JPA save() contract guarantees non-null return
    void testUpdateStudent() {
        Student updatedStudent = new Student();
        updatedStudent.setFirstName("Pedro");
        updatedStudent.setLastName("García");
        updatedStudent.setEmail("pedro.garcia@email.com");
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.existsByEmail(anyString())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);
        
        Student result = studentService.updateStudent(1L, updatedStudent);
        
        assertNotNull(result);
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }
    
    @Test
    @DisplayName("Should throw exception when updating non-existent student")
    @SuppressWarnings("null") // MessageSource.getMessage and LocaleContextHolder.getLocale are guaranteed non-null
    void testUpdateStudent_NotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Student not found");
        
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(999L, testStudent);
        });
        
        verify(studentRepository, times(1)).findById(999L);
        verify(studentRepository, never()).save(any(Student.class));
    }
    
    @Test
    @DisplayName("Should delete a student")
    void testDelete() {
        doNothing().when(studentRepository).deleteById(1L);
        
        studentService.delete(1L);
        
        verify(studentRepository, times(1)).deleteById(1L);
    }
}
