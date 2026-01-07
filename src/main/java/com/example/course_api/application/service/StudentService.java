package com.example.course_api.application.service;

import com.example.course_api.application.port.input.StudentUseCase;
import com.example.course_api.application.port.output.StudentRepositoryPort;
import com.example.course_api.domain.exception.DuplicateEmailException;
import com.example.course_api.domain.exception.StudentNotFoundException;
import com.example.course_api.domain.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements StudentUseCase {

    private final StudentRepositoryPort studentRepositoryPort;
    private final MessageSource messageSource;

    @Autowired
    public StudentService(StudentRepositoryPort studentRepositoryPort, MessageSource messageSource) {
        this.studentRepositoryPort = studentRepositoryPort;
        this.messageSource = messageSource;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepositoryPort.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepositoryPort.findById(id);
    }

    @Override
    public Student createStudent(Student student) {
        if (studentRepositoryPort.existsByEmail(student.getEmail())) {
            String message = messageSource.getMessage("Email.student.unique", null, LocaleContextHolder.getLocale());
            throw new DuplicateEmailException(student.getEmail(), message);
        }
        return studentRepositoryPort.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student existing = studentRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("Student.notfound", null, LocaleContextHolder.getLocale());
                    return new StudentNotFoundException(message);
                });

        if (!existing.getEmail().equals(student.getEmail()) && 
            studentRepositoryPort.existsByEmail(student.getEmail())) {
            String message = messageSource.getMessage("Email.student.unique", null, LocaleContextHolder.getLocale());
            throw new DuplicateEmailException(student.getEmail(), message);
        }

        existing.update(student.getFirstName(), student.getLastName(), student.getEmail());
        return studentRepositoryPort.save(existing);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepositoryPort.findById(id).isPresent()) {
            String message = messageSource.getMessage("Student.notfound", null, LocaleContextHolder.getLocale());
            throw new StudentNotFoundException(message);
        }
        studentRepositoryPort.deleteById(id);
    }
}

