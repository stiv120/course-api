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
@DisplayName("Tests para StudentService")
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
    @DisplayName("Debería retornar todos los estudiantes")
    void testGetStudents() {
        List<Student> students = Arrays.asList(testStudent);
        when(studentRepository.findAll()).thenReturn(students);
        
        List<Student> result = studentService.getStudents();
        
        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getFirstName());
        verify(studentRepository, times(1)).findAll();
    }
    
    @Test
    @DisplayName("Debería retornar un estudiante por ID")
    void testGetStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        
        Optional<Student> result = studentService.getStudent(1L);
        
        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getFirstName());
        verify(studentRepository, times(1)).findById(1L);
    }
    
    @Test
    @DisplayName("Debería retornar Optional vacío si el estudiante no existe")
    void testGetStudent_NotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());
        
        Optional<Student> result = studentService.getStudent(999L);
        
        assertFalse(result.isPresent());
        verify(studentRepository, times(1)).findById(999L);
    }
    
    @Test
    @DisplayName("Debería crear un estudiante exitosamente")
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
    @DisplayName("Debería lanzar excepción si el email ya existe")
    void testCreateStudent_DuplicateEmail() {
        when(studentRepository.existsByEmail(testStudent.getEmail())).thenReturn(true);
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Email ya existe");
        
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.createStudent(testStudent);
        });
        
        verify(studentRepository, times(1)).existsByEmail(testStudent.getEmail());
        verify(studentRepository, never()).save(any(Student.class));
    }
    
    @Test
    @DisplayName("Debería actualizar un estudiante existente")
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
    @DisplayName("Debería lanzar excepción al actualizar estudiante inexistente")
    void testUpdateStudent_NotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Estudiante no encontrado");
        
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(999L, testStudent);
        });
        
        verify(studentRepository, times(1)).findById(999L);
        verify(studentRepository, never()).save(any(Student.class));
    }
    
    @Test
    @DisplayName("Debería eliminar un estudiante")
    void testDelete() {
        doNothing().when(studentRepository).deleteById(1L);
        
        studentService.delete(1L);
        
        verify(studentRepository, times(1)).deleteById(1L);
    }
}

