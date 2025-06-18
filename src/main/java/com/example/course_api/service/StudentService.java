package com.example.course_api.service;

import com.example.course_api.entity.Student;
import com.example.course_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private MessageSource messageSource;

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudent(Long id){
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException(messageSource.getMessage("Email.student.unique", null, LocaleContextHolder.getLocale()));
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student student) {
        Student existing = studentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(messageSource.getMessage("Student.notfound", null, LocaleContextHolder.getLocale())));

        if (!existing.getEmail().equals(student.getEmail()) && studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException(messageSource.getMessage("Email.student.unique", null, LocaleContextHolder.getLocale()));
        }

        existing.setFirstName(student.getFirstName());
        existing.setLastName(student.getLastName());
        existing.setEmail(student.getEmail());

        return studentRepository.save(existing);
    }

    public void delete(Long id){
        studentRepository.deleteById(id);
    }
}
