package com.example.course_api.examples;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

@DisplayName("Tests for PrimeNumberWithThreads")
class PrimeNumberWithThreadsTest {
    
    @Test
    @DisplayName("Should find prime numbers using threads")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testFindPrimesWithThreads() {
        assertDoesNotThrow(() -> {
            PrimeNumberWithThreads.findPrimesWithThreads(4, 10);
        });
    }
    
    @Test
    @DisplayName("Should complete sequential search without errors")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testFindPrimesSequential() {
        assertDoesNotThrow(() -> {
            PrimeNumberWithThreads.findPrimesSequential(10);
        });
    }
    
    @Test
    @DisplayName("Should handle the case with 1 thread correctly")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testFindPrimesWithSingleThread() {
        assertDoesNotThrow(() -> {
            PrimeNumberWithThreads.findPrimesWithThreads(1, 5);
        });
    }
}
