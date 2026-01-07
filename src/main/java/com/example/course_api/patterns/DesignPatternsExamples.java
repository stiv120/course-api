package com.example.course_api.patterns;

import java.util.HashMap;
import java.util.Map;

class DatabaseConnection {
    private static DatabaseConnection instance;
    
    private DatabaseConnection() {
        System.out.println("Creando conexión a base de datos...");
    }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public void connect() {
        System.out.println("Conectado a la base de datos");
    }
}

interface Notification {
    void send(String message);
}

class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Enviando email: " + message);
    }
}

class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Enviando SMS: " + message);
    }
}

class PushNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Enviando push: " + message);
    }
}

class NotificationFactory {
    public static Notification createNotification(String type) {
        return switch (type.toLowerCase()) {
            case "email" -> new EmailNotification();
            case "sms" -> new SMSNotification();
            case "push" -> new PushNotification();
            default -> throw new IllegalArgumentException("Tipo de notificación no soportado: " + type);
        };
    }
}

interface DiscountStrategy {
    double calculateDiscount(double amount);
}

class StudentDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.10;
    }
}

class SeniorDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.15;
    }
}

class NoDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return 0;
    }
}

class PriceCalculator {
    private DiscountStrategy discountStrategy;
    
    public PriceCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }
    
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }
    
    public double calculateFinalPrice(double amount) {
        double discount = discountStrategy.calculateDiscount(amount);
        return amount - discount;
    }
}

interface StudentRepository {
    Student findById(Long id);
    void save(Student student);
    void delete(Long id);
}

class InMemoryStudentRepository implements StudentRepository {
    private Map<Long, Student> students = new HashMap<>();
    private Long nextId = 1L;
    
    @Override
    public Student findById(Long id) {
        return students.get(id);
    }
    
    @Override
    public void save(Student student) {
        if (student.getId() == null) {
            student.setId(nextId++);
        }
        students.put(student.getId(), student);
    }
    
    @Override
    public void delete(Long id) {
        students.remove(id);
    }
}

class Student {
    private Long id;
    private String name;
    
    public Student(String name) {
        this.name = name;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
}

interface Observer {
    void update(String message);
}

interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(String message);
}

class NewsAgency implements Subject {
    private java.util.List<Observer> observers = new java.util.ArrayList<>();
    
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
    
    public void publishNews(String news) {
        System.out.println("Publicando noticia: " + news);
        notifyObservers(news);
    }
}

class NewsChannel implements Observer {
    private String channelName;
    
    public NewsChannel(String channelName) {
        this.channelName = channelName;
    }
    
    @Override
    public void update(String message) {
        System.out.println(channelName + " recibió: " + message);
    }
}
