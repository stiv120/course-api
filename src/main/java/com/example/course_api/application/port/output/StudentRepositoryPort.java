package com.example.course_api.application.port.output;

import com.example.course_api.domain.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepositoryPort {
    Student save(Student student);
    Optional<Student> findById(Long id);
    List<Student> findAll();
    void deleteById(Long id);
    boolean existsByEmail(String email);
}

