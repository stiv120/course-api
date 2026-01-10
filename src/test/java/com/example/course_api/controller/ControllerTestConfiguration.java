package com.example.course_api.controller;

import com.example.course_api.service.StudentService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@TestConfiguration
@ComponentScan(
        basePackages = {"com.example.course_api.controller", "com.example.course_api.service"},
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {StudentController.class, StudentService.class}
        )
)
public class ControllerTestConfiguration {
}
