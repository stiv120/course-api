package com.example.course_api.examples;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumberGenerator {
    
    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static List<Integer> generateFirstNPrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        int number = 2;
        
        while (primes.size() < n) {
            if (isPrime(number)) {
                primes.add(number);
            }
            number++;
        }
        
        return primes;
    }
    
    public static void printFirstNPrimes(int n) {
        List<Integer> primes = generateFirstNPrimes(n);
        System.out.println("Los primeros " + n + " números primos son:");
        for (int i = 0; i < primes.size(); i++) {
            System.out.println((i + 1) + ". " + primes.get(i));
        }
    }
    
    public static void main(String[] args) {
        printFirstNPrimes(10);
    }
}

