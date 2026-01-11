package com.example.course_api.repository;

import com.example.course_api.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests for StudentRepository")
class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setFirstName("Juan");
        testStudent.setLastName("Pérez");
        testStudent.setEmail("juan.perez@email.com");
    }

    @Test
    @DisplayName("Should save a student correctly")
    void testSaveStudent() {
        @SuppressWarnings("null") // Spring Data JPA save() contract guarantees non-null return
        Student saved = studentRepository.save(testStudent);
        assertNotNull(saved);
        assertNotNull(saved.getStudentId());
        assertEquals("Juan", saved.getFirstName());
        assertEquals("Pérez", saved.getLastName());
        assertEquals("juan.perez@email.com", saved.getEmail());
    }

    @Test
    @DisplayName("Should find a student by ID")
    void testFindById() {
        Student saved = entityManager.persistAndFlush(testStudent);
        assertNotNull(saved);
        Long studentId = saved.getStudentId();
        assertNotNull(studentId);

        Optional<Student> found = studentRepository.findById(studentId);

        assertTrue(found.isPresent());
        assertEquals("Juan", found.get().getFirstName());
        assertEquals(studentId, found.get().getStudentId());
    }

    @Test
    @DisplayName("Should return empty Optional if student does not exist")
    void testFindById_NotFound() {
        Optional<Student> found = studentRepository.findById(999L);

        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should find all students")
    void testFindAll() {
        Student student1 = new Student();
        student1.setFirstName("María");
        student1.setLastName("González");
        student1.setEmail("maria.gonzalez@email.com");

        Student student2 = new Student();
        student2.setFirstName("Pedro");
        student2.setLastName("García");
        student2.setEmail("pedro.garcia@email.com");

        entityManager.persistAndFlush(testStudent);
        entityManager.persistAndFlush(student1);
        entityManager.persistAndFlush(student2);

        List<Student> students = studentRepository.findAll();

        assertEquals(3, students.size());
    }

    @Test
    @DisplayName("Should verify if an email exists")
    void testExistsByEmail() {
        entityManager.persistAndFlush(testStudent);

        boolean exists = studentRepository.existsByEmail("juan.perez@email.com");
        boolean notExists = studentRepository.existsByEmail("otro.email@email.com");

        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    @DisplayName("Should update an existing student")
    void testUpdateStudent() {
        Student saved = entityManager.persistAndFlush(testStudent);
        assertNotNull(saved);

        saved.setFirstName("Pedro");
        saved.setLastName("García");
        Student updated = studentRepository.save(saved);
        assertNotNull(updated);

        assertEquals("Pedro", updated.getFirstName());
        assertEquals("García", updated.getLastName());
        assertEquals(saved.getStudentId(), updated.getStudentId());
    }

    @Test
    @DisplayName("Should delete a student")
    void testDeleteStudent() {
        Student saved = entityManager.persistAndFlush(testStudent);
        assertNotNull(saved);
        Long studentId = saved.getStudentId();
        assertNotNull(studentId);

        studentRepository.deleteById(studentId);

        Optional<Student> found = studentRepository.findById(studentId);
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should return empty list when there are no students")
    void testFindAll_Empty() {
        List<Student> students = studentRepository.findAll();

        assertTrue(students.isEmpty());
    }

    @Test
    @DisplayName("Should verify that existsByEmail works correctly")
    void testEmailUniqueness() {
        Student student1 = new Student();
        student1.setFirstName("Juan");
        student1.setLastName("Pérez");
        student1.setEmail("test@email.com");

        entityManager.persistAndFlush(student1);

        boolean exists = studentRepository.existsByEmail("test@email.com");
        boolean notExists = studentRepository.existsByEmail("otro@email.com");

        assertTrue(exists);
        assertFalse(notExists);
    }
}
