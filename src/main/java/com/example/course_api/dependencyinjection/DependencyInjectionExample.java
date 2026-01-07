package com.example.course_api.dependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DEPENDENCY INJECTION EXAMPLES
 * 
 * Dependency Injection (DI) is a design pattern where objects receive their dependencies
 * from an external source rather than creating them internally.
 * 
 * Spring Framework provides DI through:
 * - @Autowired annotation
 * - Constructor injection (recommended)
 * - Setter injection
 * - Field injection
 */

// Service interface
interface NotificationService {
    void send(String message);
}

// Implementation 1
@Service
class EmailNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}

// Implementation 2
@Service
class SMSNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

// ============================================
// CONSTRUCTOR INJECTION (RECOMMENDED)
// ============================================
@Service
class UserServiceConstructor {
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    
    // Constructor injection - Spring automatically injects dependencies
    public UserServiceConstructor(NotificationService notificationService, 
                                  UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }
    
    public void notifyUser(Long userId, String message) {
        userRepository.findById(userId);
        notificationService.send(message);
    }
}

// ============================================
// FIELD INJECTION (Less recommended)
// ============================================
@Service
class UserServiceField {
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private UserRepository userRepository;
    
    public void notifyUser(Long userId, String message) {
        userRepository.findById(userId);
        notificationService.send(message);
    }
}

// ============================================
// SETTER INJECTION
// ============================================
@Service
class UserServiceSetter {
    private NotificationService notificationService;
    private UserRepository userRepository;
    
    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void notifyUser(Long userId, String message) {
        userRepository.findById(userId);
        notificationService.send(message);
    }
}

// Repository interface
interface UserRepository {
    User findById(Long id);
}

@Service
class UserRepositoryImpl implements UserRepository {
    @Override
    public User findById(Long id) {
        return new User(id, "John Doe");
    }
}

class User {
    private Long id;
    private String name;
    
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Long getId() { return id; }
    public String getName() { return name; }
}

/**
 * WHY USE DEPENDENCY INJECTION?
 * 
 * 1. Loose Coupling: Classes don't depend on concrete implementations
 * 2. Testability: Easy to mock dependencies in tests
 * 3. Flexibility: Can swap implementations without changing code
 * 4. Single Responsibility: Classes focus on their core logic
 * 
 * SPRING FRAMEWORK BENEFITS:
 * - Automatic dependency resolution
 * - Singleton pattern by default
 * - Lifecycle management
 * - AOP (Aspect-Oriented Programming) support
 */

