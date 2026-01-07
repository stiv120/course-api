package com.example.course_api.oop.interfaces;

public class CreditCardProcessor implements PaymentProcessor {
    private String cardNumber;
    private String cardHolder;

    public CreditCardProcessor(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public void processPayment(double amount) {
        if (validatePayment(amount)) {
            System.out.println("Procesando pago de $" + amount + " con tarjeta de crédito");
            System.out.println("Tarjeta: " + maskCardNumber(cardNumber));
            System.out.println("Titular: " + cardHolder);
            double tax = calculateTax(amount);
            System.out.println("Impuesto: $" + tax);
            System.out.println("Total: $" + (amount + tax));
        } else {
            System.out.println("Pago no válido");
        }
    }

    @Override
    public boolean validatePayment(double amount) {
        return amount > 0 && cardNumber != null && !cardNumber.isEmpty();
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() > 4) {
            return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }
}


