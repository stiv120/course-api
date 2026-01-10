package com.example.course_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import com.example.course_api.repository.StudentRepository;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "app.controller.enabled=false"
})
@ActiveProfiles("test")
class CourseApiApplicationTests {

    @MockBean
    private StudentRepository studentRepository;

	@Test
	void contextLoads() {
	}

}
