package com.example.course_api.service;

import com.example.course_api.entity.Student;
import com.example.course_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Optional;

@ConditionalOnProperty(name = "app.controller.enabled", havingValue = "true", matchIfMissing = false)
@Service("controllerStudentService")
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final MessageSource messageSource;

    @Autowired
    public StudentService(final StudentRepository studentRepository, final MessageSource messageSource) {
        this.studentRepository = studentRepository;
        this.messageSource = messageSource;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @SuppressWarnings("null") // findById parameter is validated by caller, Long is non-null primitive wrapper
    public Optional<Student> getStudent(final Long id) {
        return studentRepository.findById(id);
    }

    @SuppressWarnings("null") // MessageSource.getMessage and LocaleContextHolder.getLocale are guaranteed non-null, save() returns non-null
    public Student createStudent(final Student student) {
        validateEmailNotExists(student.getEmail());
        return studentRepository.save(student);
    }

    @SuppressWarnings("null") // findById parameter and save() return are guaranteed non-null, MessageSource.getMessage and LocaleContextHolder.getLocale are non-null
    public Student updateStudent(final Long id, final Student student) {
        final Student existing = findStudentById(id);
        
        if (isEmailChanged(existing, student)) {
            validateEmailNotExists(student.getEmail());
        }

        updateStudentFields(existing, student);
        return studentRepository.save(existing);
    }
    
    private void validateEmailNotExists(final String email) {
        if (studentRepository.existsByEmail(email)) {
            final String message = getLocalizedMessage("Email.student.unique");
            throw new IllegalArgumentException(message);
        }
    }
    
    private Student findStudentById(final Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> {
                final String message = getLocalizedMessage("Student.notfound");
                return new IllegalArgumentException(message);
            });
    }
    
    private boolean isEmailChanged(final Student existing, final Student updated) {
        return !existing.getEmail().equals(updated.getEmail());
    }
    
    private void updateStudentFields(final Student existing, final Student updated) {
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
    }
    
    private String getLocalizedMessage(final String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    @SuppressWarnings("null") // deleteById parameter is validated by caller, Long is non-null primitive wrapper
    public void delete(final Long id) {
        studentRepository.deleteById(id);
    }
}
