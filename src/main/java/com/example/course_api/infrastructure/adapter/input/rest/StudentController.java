package com.example.course_api.infrastructure.adapter.input.rest;

import com.example.course_api.application.port.input.StudentUseCase;
import com.example.course_api.domain.model.Student;
import com.example.course_api.infrastructure.adapter.input.rest.dto.StudentRequest;
import com.example.course_api.infrastructure.adapter.input.rest.dto.StudentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentUseCase studentUseCase;

    public StudentController(StudentUseCase studentUseCase) {
        this.studentUseCase = studentUseCase;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAll() {
        List<StudentResponse> students = studentUseCase.getAllStudents().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long studentId) {
        return studentUseCase.getStudentById(studentId)
                .map(student -> ResponseEntity.ok(toResponse(student)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        Student student = new Student(request.getFirstName(), request.getLastName(), request.getEmail());
        Student created = studentUseCase.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long studentId,
            @Valid @RequestBody StudentRequest request) {
        Student student = new Student(request.getFirstName(), request.getLastName(), request.getEmail());
        Student updated = studentUseCase.updateStudent(studentId, student);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentUseCase.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    private StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }
}

