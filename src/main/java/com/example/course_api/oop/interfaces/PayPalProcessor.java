package com.example.course_api.oop.interfaces;

public class PayPalProcessor implements PaymentProcessor {
    private String email;

    public PayPalProcessor(String email) {
        this.email = email;
    }

    @Override
    public void processPayment(double amount) {
        if (validatePayment(amount)) {
            System.out.println("Procesando pago de $" + amount + " con PayPal");
            System.out.println("Email: " + email);
            double tax = calculateTax(amount);
            System.out.println("Impuesto: $" + tax);
            System.out.println("Total: $" + (amount + tax));
        } else {
            System.out.println("Pago no válido");
        }
    }

    @Override
    public boolean validatePayment(double amount) {
        return amount > 0 && email != null && email.contains("@");
    }
}


