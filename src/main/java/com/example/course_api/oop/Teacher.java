package com.example.course_api.oop;

public class Teacher extends Person {
    private String teacherId;
    private String department;

    public Teacher(String name, String email, int age, String teacherId, String department) {
        super(name, email, age);
        this.teacherId = teacherId;
        this.department = department;
    }

    @Override
    public String getRole() {
        return "Profesor";
    }

    @Override
    public String introduce() {
        return super.introduce() + ". Enseño en el departamento de " + department;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getDepartment() {
        return department;
    }
}


