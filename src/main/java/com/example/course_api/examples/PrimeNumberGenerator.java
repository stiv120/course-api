package com.example.course_api.examples;

import java.util.ArrayList;
import java.util.List;

/**
 * Prime number calculator following SOLID principles and Clean Code practices.
 * 
 * <p>This class provides functionality to:
 * <ul>
 *   <li>Validate if a number is prime (Single Responsibility: primality check)</li>
 *   <li>Generate lists of prime numbers (Single Responsibility: prime generation)</li>
 * </ul>
 * 
 * <p>Presentation logic has been separated to maintain Single Responsibility Principle.
 * 
 * @author Stiven Chávez
 * @since 2026
 */
public final class PrimeNumberGenerator {
    
    private static final int MIN_PRIME_NUMBER = 2;
    
    private PrimeNumberGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Determines if a given number is prime.
     * 
     * <p>Applies optimized primality test:
     * <ul>
     *   <li>Numbers less than 2 are not prime</li>
     *   <li>2 is the only even prime number</li>
     *   <li>Even numbers greater than 2 are not prime</li>
     *   <li>Odd numbers are checked for divisors up to √number</li>
     * </ul>
     * 
     * @param candidateNumber the number to check for primality
     * @return {@code true} if the number is prime, {@code false} otherwise
     */
    public static boolean isPrime(final int candidateNumber) {
        if (!isValidForPrimalityCheck(candidateNumber)) {
            return false;
        }
        
        return isFirstPrimeNumber(candidateNumber) || isOddPrime(candidateNumber);
    }
    
    /**
     * Generates the first N prime numbers.
     * 
     * @param count the number of prime numbers to generate (must be non-negative)
     * @return a list containing the first N prime numbers in ascending order
     * @throws IllegalArgumentException if count is negative
     */
    public static List<Integer> generateFirstNPrimes(final int count) {
        validateCount(count);
        
        if (count == 0) {
            return new ArrayList<>();
        }
        
        return collectPrimeNumbers(count);
    }
    
    private static boolean isValidForPrimalityCheck(final int number) {
        return !isLessThanMinimumPrime(number);
    }
    
    private static boolean isLessThanMinimumPrime(final int number) {
        return number < MIN_PRIME_NUMBER;
    }
    
    private static boolean isFirstPrimeNumber(final int number) {
        return number == MIN_PRIME_NUMBER;
    }
    
    private static boolean isOddPrime(final int number) {
        return isOddNumber(number) && hasNoOddDivisors(number);
    }
    
    private static boolean isOddNumber(final int number) {
        return number % 2 != 0;
    }
    
    private static boolean hasNoOddDivisors(final int number) {
        final int squareRoot = calculateSquareRoot(number);
        
        for (int divisor = 3; divisor <= squareRoot; divisor += 2) {
            if (isDivisibleBy(number, divisor)) {
                return false;
            }
        }
        
        return true;
    }
    
    private static int calculateSquareRoot(final int number) {
        return (int) Math.sqrt(number);
    }
    
    private static boolean isDivisibleBy(final int dividend, final int divisor) {
        return dividend % divisor == 0;
    }
    
    private static void validateCount(final int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count must be non-negative, but was: " + count);
        }
    }
    
    private static List<Integer> collectPrimeNumbers(final int count) {
        final List<Integer> primeNumbers = new ArrayList<>(count);
        int currentNumber = MIN_PRIME_NUMBER;
        
        while (primeNumbers.size() < count) {
            if (isPrime(currentNumber)) {
                primeNumbers.add(currentNumber);
            }
            currentNumber++;
        }
        
        return primeNumbers;
    }
}