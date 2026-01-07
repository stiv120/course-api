package com.example.course_api.solid;

import com.example.course_api.oop.interfaces.PaymentProcessor;

class BadStudentManager {
    public void saveStudent(String name) {
        System.out.println("Guardando estudiante: " + name);
    }
    
    public void sendEmail(String email) {
        System.out.println("Enviando email a: " + email);
    }
    
    public void generateReport() {
        System.out.println("Generando reporte...");
    }
}

class StudentRepository {
    public void save(String name) {
        System.out.println("Guardando estudiante: " + name);
    }
}

class EmailService {
    public void sendEmail(String email) {
        System.out.println("Enviando email a: " + email);
    }
}

class ReportGenerator {
    public void generateReport() {
        System.out.println("Generando reporte...");
    }
}

class PaymentService {
    public void processPayment(PaymentProcessor processor, double amount) {
        processor.processPayment(amount);
    }
}

abstract class Shape {
    public abstract double calculateArea();
}

class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
}

class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class AreaCalculator {
    public double calculateTotalArea(Shape[] shapes) {
        double total = 0;
        for (Shape shape : shapes) {
            total += shape.calculateArea();
        }
        return total;
    }
}

interface Worker {
    void work();
    void eat();
    void sleep();
}

class Human implements Worker {
    @Override
    public void work() { System.out.println("Human working"); }
    @Override
    public void eat() { System.out.println("Human eating"); }
    @Override
    public void sleep() { System.out.println("Human sleeping"); }
}

class Robot implements Worker {
    @Override
    public void work() { System.out.println("Robot working"); }
    @Override
    public void eat() { 
        throw new UnsupportedOperationException("Robots don't eat");
    }
    @Override
    public void sleep() { 
        throw new UnsupportedOperationException("Robots don't sleep");
    }
}

interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

interface Sleepable {
    void sleep();
}

class HumanWorker implements Workable, Eatable, Sleepable {
    @Override
    public void work() { System.out.println("Human working"); }
    @Override
    public void eat() { System.out.println("Human eating"); }
    @Override
    public void sleep() { System.out.println("Human sleeping"); }
}

class RobotWorker implements Workable {
    @Override
    public void work() { System.out.println("Robot working"); }
}

class BadNotificationService {
    private EmailService emailService = new EmailService();
    
    public void notify(String message) {
        emailService.sendEmail(message);
    }
}

interface NotificationService {
    void send(String message);
}

class EmailNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}

class SMSNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

class GoodNotificationService {
    private NotificationService notificationService;
    
    public GoodNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    public void notify(String message) {
        notificationService.send(message);
    }
}
