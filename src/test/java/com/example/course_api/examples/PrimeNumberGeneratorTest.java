package com.example.course_api.examples;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@DisplayName("Tests for PrimeNumberGenerator")
class PrimeNumberGeneratorTest {
    
    @Test
    @DisplayName("Should return false for numbers less than 2")
    void testIsPrime_WithNumbersLessThan2() {
        assertFalse(PrimeNumberGenerator.isPrime(0));
        assertFalse(PrimeNumberGenerator.isPrime(1));
        assertFalse(PrimeNumberGenerator.isPrime(-5));
    }
    
    @Test
    @DisplayName("Should return true for number 2")
    void testIsPrime_WithNumber2() {
        assertTrue(PrimeNumberGenerator.isPrime(2));
    }
    
    @Test
    @DisplayName("Should correctly identify prime numbers")
    void testIsPrime_WithPrimeNumbers() {
        assertTrue(PrimeNumberGenerator.isPrime(3));
        assertTrue(PrimeNumberGenerator.isPrime(5));
        assertTrue(PrimeNumberGenerator.isPrime(7));
        assertTrue(PrimeNumberGenerator.isPrime(11));
        assertTrue(PrimeNumberGenerator.isPrime(13));
        assertTrue(PrimeNumberGenerator.isPrime(17));
        assertTrue(PrimeNumberGenerator.isPrime(19));
        assertTrue(PrimeNumberGenerator.isPrime(23));
    }
    
    @Test
    @DisplayName("Should correctly identify non-prime numbers")
    void testIsPrime_WithNonPrimeNumbers() {
        assertFalse(PrimeNumberGenerator.isPrime(4));
        assertFalse(PrimeNumberGenerator.isPrime(6));
        assertFalse(PrimeNumberGenerator.isPrime(8));
        assertFalse(PrimeNumberGenerator.isPrime(9));
        assertFalse(PrimeNumberGenerator.isPrime(10));
        assertFalse(PrimeNumberGenerator.isPrime(15));
        assertFalse(PrimeNumberGenerator.isPrime(20));
    }
    
    @Test
    @DisplayName("Should generate the first 10 prime numbers correctly")
    void testGenerateFirstNPrimes_With10() {
        List<Integer> primes = PrimeNumberGenerator.generateFirstNPrimes(10);
        
        assertEquals(10, primes.size());
        assertEquals(2, primes.get(0));
        assertEquals(3, primes.get(1));
        assertEquals(5, primes.get(2));
        assertEquals(7, primes.get(3));
        assertEquals(11, primes.get(4));
        assertEquals(13, primes.get(5));
        assertEquals(17, primes.get(6));
        assertEquals(19, primes.get(7));
        assertEquals(23, primes.get(8));
        assertEquals(29, primes.get(9));
    }
    
    @Test
    @DisplayName("Should generate the first 5 prime numbers")
    void testGenerateFirstNPrimes_With5() {
        List<Integer> primes = PrimeNumberGenerator.generateFirstNPrimes(5);
        
        assertEquals(5, primes.size());
        assertEquals(List.of(2, 3, 5, 7, 11), primes);
    }
    
    @Test
    @DisplayName("Should return empty list for n = 0")
    void testGenerateFirstNPrimes_WithZero() {
        List<Integer> primes = PrimeNumberGenerator.generateFirstNPrimes(0);
        assertTrue(primes.isEmpty());
    }
    
    @Test
    @DisplayName("Should generate a single prime number for n = 1")
    void testGenerateFirstNPrimes_WithOne() {
        List<Integer> primes = PrimeNumberGenerator.generateFirstNPrimes(1);
        assertEquals(1, primes.size());
        assertEquals(2, primes.get(0));
    }
}
