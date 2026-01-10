package com.example.course_api.examples;

import java.util.List;

/**
 * Responsible for printing prime numbers to the console.
 * 
 * <p>This class follows the Single Responsibility Principle by handling
 * only the presentation/formatting of prime numbers, while the calculation
 * logic remains in {@link PrimeNumberGenerator}.
 * 
 * @author Stiven Chávez
 * @since 2026
 */
public final class PrimeNumberPrinter {
    
    private PrimeNumberPrinter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Prints the first N prime numbers to the console.
     * 
     * @param count the number of prime numbers to print
     */
    public static void printFirstNPrimes(final int count) {
        final List<Integer> primeNumbers = PrimeNumberGenerator.generateFirstNPrimes(count);
        printPrimes(primeNumbers, count);
    }
    
    /**
     * Prints a list of prime numbers to the console with formatted output.
     * 
     * @param primeNumbers the list of prime numbers to print
     * @param expectedCount the expected count for the header message
     */
    public static void printPrimes(final List<Integer> primeNumbers, final int expectedCount) {
        System.out.println("The first " + expectedCount + " prime numbers are:");
        
        for (int index = 0; index < primeNumbers.size(); index++) {
            final int position = index + 1;
            final int primeNumber = primeNumbers.get(index);
            System.out.println(position + ". " + primeNumber);
        }
    }
    
    /**
     * Main method for demonstration purposes.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(final String[] args) {
        printFirstNPrimes(10);
    }
}
