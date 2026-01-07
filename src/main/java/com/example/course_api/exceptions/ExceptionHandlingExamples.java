package com.example.course_api.exceptions;

import java.io.FileReader;
import java.io.IOException;

public class ExceptionHandlingExamples {

    public void basicExceptionHandling() {
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Error: División por cero - " + e.getMessage());
        }
    }

    public void multipleCatchBlocks() {
        try {
            String str = null;
            int length = str.length();
            int num = Integer.parseInt("abc");
        } catch (NullPointerException e) {
            System.out.println("Error: Objeto nulo - " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Formato de número inválido - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }

    public void finallyExample() {
        try {
            System.out.println("Ejecutando código...");
            int result = 10 / 2;
            System.out.println("Resultado: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error capturado");
        } finally {
            System.out.println("Este código siempre se ejecuta (finally)");
        }
    }

    public void checkedExceptionExample() {
        try {
            FileReader file = new FileReader("archivo.txt");
            file.read();
            file.close();
        } catch (IOException e) {
            System.out.println("Error de IO: " + e.getMessage());
        }
    }

    public void methodWithThrows() throws IOException {
        FileReader file = new FileReader("archivo.txt");
        file.close();
    }

    public void throwCustomException() {
        try {
            validateAge(15);
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        }
    }

    private void validateAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("La edad debe ser mayor a 18 años");
        }
    }

    static class StudentNotFoundException extends Exception {
        public StudentNotFoundException(String message) {
            super(message);
        }
    }

    public void customExceptionExample() {
        try {
            findStudent(999L);
        } catch (StudentNotFoundException e) {
            System.out.println("Estudiante no encontrado: " + e.getMessage());
        }
    }

    private void findStudent(Long id) throws StudentNotFoundException {
        if (id == null || id < 1) {
            throw new StudentNotFoundException("ID de estudiante inválido: " + id);
        }
    }

    public void tryWithResourcesExample() {
        try (FileReader file = new FileReader("archivo.txt")) {
            file.read();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void finalVsFinally() {
        final int constant = 100;
        
        try {
            System.out.println("Código");
        } finally {
            System.out.println("Siempre se ejecuta");
        }
    }
}
