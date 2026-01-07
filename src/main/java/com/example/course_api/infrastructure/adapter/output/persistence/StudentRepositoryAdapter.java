package com.example.course_api.infrastructure.adapter.output.persistence;

import com.example.course_api.application.port.output.StudentRepositoryPort;
import com.example.course_api.domain.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StudentRepositoryAdapter implements StudentRepositoryPort {

    private final StudentJpaRepository studentJpaRepository;

    public StudentRepositoryAdapter(StudentJpaRepository studentJpaRepository) {
        this.studentJpaRepository = studentJpaRepository;
    }

    @Override
    public Student save(Student student) {
        StudentJpaEntity entity = toJpaEntity(student);
        StudentJpaEntity saved = studentJpaRepository.save(entity);
        return toDomainModel(saved);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentJpaRepository.findById(id)
                .map(this::toDomainModel);
    }

    @Override
    public List<Student> findAll() {
        return studentJpaRepository.findAll().stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        studentJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return studentJpaRepository.existsByEmail(email);
    }

    private StudentJpaEntity toJpaEntity(Student student) {
        StudentJpaEntity entity = new StudentJpaEntity();
        entity.setStudentId(student.getStudentId());
        entity.setFirstName(student.getFirstName());
        entity.setLastName(student.getLastName());
        entity.setEmail(student.getEmail());
        return entity;
    }

    private Student toDomainModel(StudentJpaEntity entity) {
        return new Student(
                entity.getStudentId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail()
        );
    }
}

