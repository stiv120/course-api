package com.example.course_api.controller;

import com.example.course_api.entity.Student;
import com.example.course_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAll(){
        return studentService.getStudents();
    }

    @GetMapping("/{studentId}")
    public Optional<Student> getById(@PathVariable("studentId") Long studentId){
        return studentService.getStudent(studentId);
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student){
        studentService.createStudent(student);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Long studentId, @Valid @RequestBody Student student){
        studentService.updateStudent(studentId, student);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}")
    public void saveOrUpdate(@PathVariable("studentId") Long studentId){
        studentService.delete(studentId);
    }
}