package com.example.course_api.application.service;

import com.example.course_api.application.port.input.StudentUseCase;
import com.example.course_api.application.port.output.StudentRepositoryPort;
import com.example.course_api.domain.exception.DuplicateEmailException;
import com.example.course_api.domain.exception.StudentNotFoundException;
import com.example.course_api.domain.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
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
    public Optional<Student> getStudentById(final Long id) {
        return studentRepositoryPort.findById(id);
    }

    @Override
    public Student createStudent(final Student student) {
        validateEmailNotExists(student.getEmail());
        return studentRepositoryPort.save(student);
    }

    @Override
    public Student updateStudent(final Long id, final Student student) {
        final Student existing = findStudentById(id);
        
        if (isEmailChanged(existing, student)) {
            validateEmailNotExists(student.getEmail());
        }

        existing.update(student.getFirstName(), student.getLastName(), student.getEmail());
        return studentRepositoryPort.save(existing);
    }

    @Override
    public void deleteStudent(final Long id) {
        findStudentById(id);
        studentRepositoryPort.deleteById(id);
    }
    
    private void validateEmailNotExists(final String email) {
        if (studentRepositoryPort.existsByEmail(email)) {
            final String message = getLocalizedMessage("Email.student.unique");
            throw new DuplicateEmailException(email, message);
        }
    }
    
    private Student findStudentById(final Long id) {
        return studentRepositoryPort.findById(id)
                .orElseThrow(this::createStudentNotFoundException);
    }
    
    private StudentNotFoundException createStudentNotFoundException() {
        final String message = getLocalizedMessage("Student.notfound");
        return new StudentNotFoundException(message);
    }
    
    private boolean isEmailChanged(final Student existing, final Student updated) {
        return !existing.getEmail().equals(updated.getEmail());
    }
    
    private String getLocalizedMessage(final String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}


