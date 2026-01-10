package com.example.course_api.application.port.input;

import com.example.course_api.domain.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentUseCase {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Long id);
    Student createStudent(Student student);
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
}


