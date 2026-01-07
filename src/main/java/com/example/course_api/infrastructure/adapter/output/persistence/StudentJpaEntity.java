package com.example.course_api.infrastructure.adapter.output.persistence;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_students")
@Data
public class StudentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(name = "email_address", unique = true, nullable = false)
    private String email;
}

