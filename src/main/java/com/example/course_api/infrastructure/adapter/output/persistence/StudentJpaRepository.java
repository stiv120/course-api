package com.example.course_api.infrastructure.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, Long> {
    boolean existsByEmail(String email);
}


