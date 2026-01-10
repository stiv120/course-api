package com.example.course_api.infrastructure.adapter.output.persistence;

import com.example.course_api.domain.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for StudentRepositoryAdapter (Hexagonal Architecture)")
class StudentRepositoryAdapterTest {

    @Mock
    private StudentJpaRepository studentJpaRepository;

    @InjectMocks
    private StudentRepositoryAdapter studentRepositoryAdapter;

    private StudentJpaEntity testEntity;
    private Student testStudent;

    @BeforeEach
    void setUp() {
        testEntity = new StudentJpaEntity();
        testEntity.setStudentId(1L);
        testEntity.setFirstName("Juan");
        testEntity.setLastName("Pérez");
        testEntity.setEmail("juan.perez@email.com");

        testStudent = new Student(1L, "Juan", "Pérez", "juan.perez@email.com");
    }

    @Test
    @DisplayName("Should save a student and return domain model")
    @SuppressWarnings("null") // Spring Data JPA save() contract guarantees non-null return
    void testSave() {
        when(studentJpaRepository.save(any(StudentJpaEntity.class))).thenReturn(testEntity);

        Student result = studentRepositoryAdapter.save(testStudent);

        assertNotNull(result);
        assertEquals(1L, result.getStudentId());
        assertEquals("Juan", result.getFirstName());
        verify(studentJpaRepository, times(1)).save(any(StudentJpaEntity.class));
    }

    @Test
    @DisplayName("Should find a student by ID")
    @SuppressWarnings("null") // Optional.of() guarantees non-null value, Long literal is non-null
    void testFindById() {
        when(studentJpaRepository.findById(1L)).thenReturn(Optional.of(testEntity));

        Optional<Student> result = studentRepositoryAdapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getFirstName());
        verify(studentJpaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty Optional if not found")
    void testFindById_NotFound() {
        when(studentJpaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Student> result = studentRepositoryAdapter.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should find all students")
    void testFindAll() {
        List<StudentJpaEntity> entities = Arrays.asList(testEntity);
        when(studentJpaRepository.findAll()).thenReturn(entities);

        List<Student> result = studentRepositoryAdapter.findAll();

        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getFirstName());
        verify(studentJpaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should delete a student")
    void testDeleteById() {
        doNothing().when(studentJpaRepository).deleteById(1L);

        studentRepositoryAdapter.deleteById(1L);

        verify(studentJpaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should verify if an email exists")
    void testExistsByEmail() {
        when(studentJpaRepository.existsByEmail("juan.perez@email.com")).thenReturn(true);
        when(studentJpaRepository.existsByEmail("otro@email.com")).thenReturn(false);

        assertTrue(studentRepositoryAdapter.existsByEmail("juan.perez@email.com"));
        assertFalse(studentRepositoryAdapter.existsByEmail("otro@email.com"));
    }
}
