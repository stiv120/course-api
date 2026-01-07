package com.example.course_api.examples;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

@DisplayName("Tests para PrimeNumberWithThreads")
class PrimeNumberWithThreadsTest {
    
    @Test
    @DisplayName("Debería encontrar números primos usando threads")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testFindPrimesWithThreads() {
        assertDoesNotThrow(() -> {
            PrimeNumberWithThreads.findPrimesWithThreads(4, 10);
        });
    }
    
    @Test
    @DisplayName("Debería completar la búsqueda secuencial sin errores")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testFindPrimesSequential() {
        assertDoesNotThrow(() -> {
            PrimeNumberWithThreads.findPrimesSequential(10);
        });
    }
    
    @Test
    @DisplayName("Debería manejar correctamente el caso con 1 thread")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testFindPrimesWithSingleThread() {
        assertDoesNotThrow(() -> {
            PrimeNumberWithThreads.findPrimesWithThreads(1, 5);
        });
    }
}

