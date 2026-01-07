package com.example.course_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.course_api")
public class CourseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseApiApplication.class, args);
	}

}
