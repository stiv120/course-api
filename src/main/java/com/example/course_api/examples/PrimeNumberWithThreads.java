package com.example.course_api.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PrimeNumberWithThreads {
    
    public static class PrimeFinderTask implements Callable<List<Integer>> {
        private final int start;
        private final int end;
        private final int maxPrimes;
        
        public PrimeFinderTask(int start, int end, int maxPrimes) {
            this.start = start;
            this.end = end;
            this.maxPrimes = maxPrimes;
        }
        
        @Override
        public List<Integer> call() {
            List<Integer> primes = new ArrayList<>();
            int count = 0;
            
            for (int i = start; i <= end && count < maxPrimes; i++) {
                if (PrimeNumberGenerator.isPrime(i)) {
                    primes.add(i);
                    count++;
                }
            }
            
            System.out.println("Thread " + Thread.currentThread().getName() + 
                             " encontró " + primes.size() + " primos en el rango [" + 
                             start + ", " + end + "]");
            return primes;
        }
    }
    
    public static void findPrimesWithThreads(int numberOfThreads, int primesToFind) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<List<Integer>>> futures = new ArrayList<>();
        
        int rangePerThread = 1000;
        int primesPerThread = primesToFind / numberOfThreads;
        
        for (int i = 0; i < numberOfThreads; i++) {
            int start = i * rangePerThread + 2;
            int end = (i + 1) * rangePerThread;
            
            PrimeFinderTask task = new PrimeFinderTask(start, end, primesPerThread);
            Future<List<Integer>> future = executor.submit(task);
            futures.add(future);
        }
        
        List<Integer> allPrimes = new ArrayList<>();
        
        try {
            for (Future<List<Integer>> future : futures) {
                allPrimes.addAll(future.get());
            }
            
            allPrimes.sort(Integer::compareTo);
            
            System.out.println("\n=== RESULTADO FINAL ===");
            System.out.println("Total de primos encontrados: " + allPrimes.size());
            System.out.println("Los primeros " + Math.min(10, allPrimes.size()) + " números primos son:");
            
            for (int i = 0; i < Math.min(10, allPrimes.size()); i++) {
                System.out.println((i + 1) + ". " + allPrimes.get(i));
            }
            
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error al ejecutar tareas: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public static void findPrimesSequential(int primesToFind) {
        System.out.println("\n=== BÚSQUEDA SECUENCIAL ===");
        long startTime = System.currentTimeMillis();
        
        List<Integer> primes = PrimeNumberGenerator.generateFirstNPrimes(primesToFind);
        
        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución: " + (endTime - startTime) + " ms");
        System.out.println("Primeros " + primes.size() + " números primos encontrados:");
        
        for (int i = 0; i < Math.min(10, primes.size()); i++) {
            System.out.println((i + 1) + ". " + primes.get(i));
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== EJEMPLO DE NÚMEROS PRIMOS CON HILOS ===\n");
        
        System.out.println("1. Búsqueda secuencial:");
        findPrimesSequential(10);
        
        System.out.println("\n2. Búsqueda paralela con 4 threads:");
        findPrimesWithThreads(4, 10);
    }
}

