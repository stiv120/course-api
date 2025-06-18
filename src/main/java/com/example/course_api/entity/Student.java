package com.example.course_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "tbl_students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long studentId;

    @NotBlank(message = "{NotBlank.student.firstName}")
    private String firstName;

    @NotBlank(message = "{NotBlank.student.lastName}")
    private String lastName;

    @NotBlank(message = "{NotBlank.student.email}")
    @Email(message = "{Email.student.email}")
    @Column(name = "email_address", unique = true, nullable = false )
    private String email;
}
