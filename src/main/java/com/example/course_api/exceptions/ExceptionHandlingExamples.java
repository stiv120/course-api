package com.example.course_api.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;

/**
 * Examples demonstrating proper exception handling following Clean Code principles.
 * 
 * <p>This class demonstrates:
 * <ul>
 *   <li>Basic exception handling</li>
 *   <li>Multiple catch blocks</li>
 *   <li>Finally blocks</li>
 *   <li>Checked and unchecked exceptions</li>
 *   <li>Custom exceptions</li>
 *   <li>Try-with-resources</li>
 * </ul>
 * 
 * @author Stiven Chávez
 * @since 2026
 */
public final class ExceptionHandlingExamples {
    
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingExamples.class);
    private static final String DEFAULT_FILE_PATH = "archivo.txt";
    private static final int MINIMUM_AGE = 18;
    private static final int DIVISOR = 0;
    
    private ExceptionHandlingExamples() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Demonstrates basic exception handling for arithmetic operations.
     * 
     * <p>Shows how to catch and handle ArithmeticException.
     */
    public static void demonstrateBasicExceptionHandling() {
        try {
            performDivision();
        } catch (final ArithmeticException exception) {
            logger.error("Division by zero error", exception);
        }
    }
    
    private static void performDivision() {
        final int dividend = 10;
        @SuppressWarnings("unused") // Result intentionally unused for demonstration
        final int result = dividend / DIVISOR; // Will throw ArithmeticException
    }

    /**
     * Demonstrates multiple catch blocks for handling different exception types.
     * 
     * <p>Shows how to handle specific exceptions before general ones.
     */
    public static void demonstrateMultipleCatchBlocks() {
        demonstrateNullPointerExceptionHandling();
        demonstrateNumberFormatExceptionHandling();
    }
    
    private static void demonstrateNullPointerExceptionHandling() {
        try {
            final String nullString = null;
            // Intentionally accessing null to demonstrate NullPointerException
            @SuppressWarnings({"unused", "null"}) // Intentionally null for demonstration
            final int length = nullString.length(); // Will throw NullPointerException
        } catch (final NullPointerException exception) {
            logger.error("Null object accessed", exception);
        }
    }
    
    private static void demonstrateNumberFormatExceptionHandling() {
        try {
            @SuppressWarnings("unused") // Result intentionally unused for demonstration
            final int parsedNumber = Integer.parseInt("abc"); // Will throw NumberFormatException
        } catch (final NumberFormatException exception) {
            logger.error("Invalid number format", exception);
        } catch (final Exception exception) {
            logger.error("General error occurred", exception);
        }
    }

    /**
     * Demonstrates the finally block which always executes.
     * 
     * <p>Useful for cleanup operations like closing resources.
     */
    public static void demonstrateFinallyBlock() {
        try {
            final int dividend = 10;
            final int divisor = 2;
            final int result = dividend / divisor;
            logger.debug("Division result: {}", result);
        } catch (final ArithmeticException exception) {
            logger.error("Arithmetic error in finally block demonstration", exception);
        } finally {
            logger.debug("Finally block executed");
        }
    }

    /**
     * Demonstrates handling checked exceptions (IOException).
     * 
     * <p>Checked exceptions must be handled or declared in method signature.
     * Uses try-with-resources for automatic resource management.
     */
    public static void demonstrateCheckedException() {
        try (final FileReader file = new FileReader(DEFAULT_FILE_PATH)) {
            file.read();
        } catch (final IOException exception) {
            printIOException(exception);
        }
    }

    /**
     * Demonstrates declaring exceptions in method signature using throws.
     * 
     * @throws IOException if file operations fail
     */
    public static void demonstrateMethodWithThrows() throws IOException {
        try (final FileReader file = new FileReader(DEFAULT_FILE_PATH)) {
            // File operations
        }
    }
    
    private static void printIOException(final IOException exception) {
        logger.error("IO Error occurred", exception);
    }

    /**
     * Demonstrates throwing and catching custom exceptions.
     */
    public static void demonstrateCustomException() {
        try {
            validateAge(15);
        } catch (final IllegalArgumentException exception) {
            logger.error("Validation error: {}", exception.getMessage(), exception);
        }
    }

    /**
     * Validates that age meets minimum requirement.
     * 
     * @param age the age to validate
     * @throws IllegalArgumentException if age is below minimum
     */
    private static void validateAge(final int age) {
        if (isAgeBelowMinimum(age)) {
            throw createAgeValidationException(age);
        }
    }
    
    private static boolean isAgeBelowMinimum(final int age) {
        return age < MINIMUM_AGE;
    }
    
    private static IllegalArgumentException createAgeValidationException(final int age) {
        final String message = String.format("Age must be at least %d years, but was: %d", MINIMUM_AGE, age);
        return new IllegalArgumentException(message);
    }

    /**
     * Custom exception for when a student is not found.
     * 
     * <p>Following Clean Code: Custom exceptions should provide meaningful context.
     */
    static final class StudentNotFoundException extends Exception {
        private static final long serialVersionUID = 1L;
        
        StudentNotFoundException(final String message) {
            super(message);
        }
    }

    /**
     * Demonstrates creating and throwing custom exceptions.
     */
    public static void demonstrateCustomExceptionExample() {
        try {
            findStudent(999L);
        } catch (final StudentNotFoundException exception) {
            logger.error("Student not found: {}", exception.getMessage(), exception);
        }
    }

    /**
     * Simulates finding a student by ID.
     * 
     * @param studentId the student ID to search for
     * @throws StudentNotFoundException if student ID is invalid or student not found
     */
    private static void findStudent(final Long studentId) throws StudentNotFoundException {
        if (isInvalidStudentId(studentId)) {
            throw createStudentNotFoundException(studentId);
        }
        // Student lookup logic would go here
    }
    
    private static boolean isInvalidStudentId(final Long studentId) {
        return studentId == null || studentId < 1;
    }
    
    private static StudentNotFoundException createStudentNotFoundException(final Long studentId) {
        return new StudentNotFoundException("Invalid student ID: " + studentId);
    }

    /**
     * Demonstrates try-with-resources for automatic resource management.
     * 
     * <p>Resources are automatically closed even if exceptions occur.
     * This is preferred over manual resource management.
     */
    public static void demonstrateTryWithResources() {
        try (final FileReader file = new FileReader(DEFAULT_FILE_PATH)) {
            file.read();
        } catch (final IOException exception) {
            printResourceException(exception);
        }
    }
    
    private static void printResourceException(final IOException exception) {
        logger.error("Resource error occurred", exception);
    }

    /**
     * Demonstrates the difference between 'final' keyword and 'finally' block.
     * 
     * <ul>
     *   <li><b>final</b>: Makes variables, methods, or classes immutable/unchangeable</li>
     *   <li><b>finally</b>: Block that always executes in try-catch structures</li>
     * </ul>
     */
    public static void demonstrateFinalVsFinally() {
        final int constant = 100;
        // Demonstrating final keyword - value cannot be reassigned
        logger.debug("Constant value: {}", constant);
        
        try {
            logger.debug("Executing code in try block");
        } finally {
            logger.debug("Finally block executed");
        }
    }
}
