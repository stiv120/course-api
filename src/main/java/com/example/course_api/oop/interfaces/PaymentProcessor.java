package com.example.course_api.oop.interfaces;

public interface PaymentProcessor {
    double TAX_RATE = 0.19;

    void processPayment(double amount);

    default double calculateTax(double amount) {
        return amount * TAX_RATE;
    }

    static String getProcessorInfo() {
        return "Payment Processor Interface v1.0";
    }

    boolean validatePayment(double amount);
}


