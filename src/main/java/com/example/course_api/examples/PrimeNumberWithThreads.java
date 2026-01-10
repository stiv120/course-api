package com.example.course_api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Parallel prime number finder using concurrent execution.
 * 
 * <p>This class demonstrates concurrent programming by distributing
 * prime number searches across multiple threads.
 * 
 * <p>Follows Single Responsibility Principle:
 * <ul>
 *   <li>PrimeFinderTask: Finds primes in a specific range</li>
 *   <li>PrimeNumberWithThreads: Orchestrates parallel execution</li>
 * </ul>
 * 
 * @author Stiven Chávez
 * @since 2026
 */
public final class PrimeNumberWithThreads {
    
    private static final Logger logger = LoggerFactory.getLogger(PrimeNumberWithThreads.class);
    private static final int DEFAULT_RANGE_PER_THREAD = 1000;
    private static final int SHUTDOWN_TIMEOUT_SECONDS = 60;
    private static final int DISPLAY_LIMIT = 10;
    
    private PrimeNumberWithThreads() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Task that finds prime numbers within a specific range.
     * 
     * <p>This inner class follows Single Responsibility Principle
     * by only being responsible for finding primes in its assigned range.
     */
    public static final class PrimeFinderTask implements Callable<List<Integer>> {
        private final int startRange;
        private final int endRange;
        private final int maximumPrimesToFind;
        
        /**
         * Creates a new task to find primes in the specified range.
         * 
         * @param startRange the start of the range (inclusive)
         * @param endRange the end of the range (inclusive)
         * @param maximumPrimesToFind maximum number of primes to find
         */
        public PrimeFinderTask(final int startRange, final int endRange, final int maximumPrimesToFind) {
            this.startRange = startRange;
            this.endRange = endRange;
            this.maximumPrimesToFind = maximumPrimesToFind;
        }
        
        @Override
        public List<Integer> call() {
            final List<Integer> foundPrimes = findPrimesInRange();
            logTaskCompletion(foundPrimes.size());
            return foundPrimes;
        }
        
        private List<Integer> findPrimesInRange() {
            final List<Integer> primes = new ArrayList<>();
            int foundCount = 0;
            
            for (int candidate = startRange; candidate <= endRange && foundCount < maximumPrimesToFind; candidate++) {
                if (PrimeNumberGenerator.isPrime(candidate)) {
                    primes.add(candidate);
                    foundCount++;
                }
            }
            
            return primes;
        }
        
        private void logTaskCompletion(final int primesFound) {
            logger.debug("Thread {} found {} primes in range [{}, {}]", 
                Thread.currentThread().getName(), primesFound, startRange, endRange);
        }
    }
    
    /**
     * Finds prime numbers using parallel execution across multiple threads.
     * 
     * @param numberOfThreads the number of threads to use
     * @param primesToFind the target number of primes to find
     * @return a sorted list of prime numbers found
     * @throws IllegalArgumentException if numberOfThreads or primesToFind is non-positive
     */
    public static List<Integer> findPrimesWithThreads(final int numberOfThreads, final int primesToFind) {
        validateInputs(numberOfThreads, primesToFind);
        
        final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        
        try {
            final List<Future<List<Integer>>> taskFutures = submitPrimeFindingTasks(
                executorService, numberOfThreads, primesToFind
            );
            
            final List<Integer> allPrimes = collectResults(taskFutures);
            allPrimes.sort(Integer::compareTo);
            
            PrimeNumberPrinter.printPrimes(allPrimes.subList(0, Math.min(DISPLAY_LIMIT, allPrimes.size())), 
                                          allPrimes.size());
            
            return allPrimes;
        } finally {
            shutdownExecutorSafely(executorService);
        }
    }
    
    private static void validateInputs(final int numberOfThreads, final int primesToFind) {
        if (numberOfThreads <= 0) {
            throw new IllegalArgumentException("Number of threads must be positive, but was: " + numberOfThreads);
        }
        if (primesToFind <= 0) {
            throw new IllegalArgumentException("Primes to find must be positive, but was: " + primesToFind);
        }
    }
    
    private static List<Future<List<Integer>>> submitPrimeFindingTasks(
            final ExecutorService executor,
            final int numberOfThreads,
            final int primesToFind) {
        
        final List<Future<List<Integer>>> futures = new ArrayList<>();
        final int primesPerThread = primesToFind / numberOfThreads;
        
        for (int threadIndex = 0; threadIndex < numberOfThreads; threadIndex++) {
            final int startRange = calculateStartRange(threadIndex);
            final int endRange = calculateEndRange(threadIndex);
            
            final PrimeFinderTask task = new PrimeFinderTask(startRange, endRange, primesPerThread);
            futures.add(executor.submit(task));
        }
        
        return futures;
    }
    
    private static int calculateStartRange(final int threadIndex) {
        return threadIndex * DEFAULT_RANGE_PER_THREAD + FIRST_PRIME_NUMBER;
    }
    
    private static int calculateEndRange(final int threadIndex) {
        return (threadIndex + 1) * DEFAULT_RANGE_PER_THREAD;
    }
    
    private static List<Integer> collectResults(final List<Future<List<Integer>>> futures) {
        final List<Integer> allPrimes = new ArrayList<>();
        
        for (final Future<List<Integer>> future : futures) {
            try {
                allPrimes.addAll(future.get());
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Task execution was interrupted", e);
                throw new RuntimeException("Task execution was interrupted", e);
            } catch (final ExecutionException e) {
                logger.error("Error executing prime finding task", e);
                throw new RuntimeException("Error executing prime finding task", e);
            }
        }
        
        return allPrimes;
    }
    
    private static void shutdownExecutorSafely(final ExecutorService executor) {
        executor.shutdown();
        
        try {
            shutdownExecutor(executor);
        } catch (final InterruptedException e) {
            handleInterruption(executor);
        }
    }
    
    private static void shutdownExecutor(final ExecutorService executor) throws InterruptedException {
        if (!executor.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }
    
    private static void handleInterruption(final ExecutorService executor) {
        logger.warn("Executor shutdown was interrupted, forcing shutdown now");
        executor.shutdownNow();
        Thread.currentThread().interrupt();
    }
    
    private static final int FIRST_PRIME_NUMBER = 2; // First prime number is 2
    
    /**
     * Finds prime numbers using sequential execution.
     * 
     * <p>Useful for performance comparison with parallel execution.
     * 
     * @param primesToFind the number of primes to find
     * @return a list of the first N prime numbers
     */
    public static List<Integer> findPrimesSequential(final int primesToFind) {
        final long startTime = System.currentTimeMillis();
        
        final List<Integer> primes = PrimeNumberGenerator.generateFirstNPrimes(primesToFind);
        
        final long endTime = System.currentTimeMillis();
        final long executionTime = endTime - startTime;
        
        logger.info("Sequential search completed in {} ms, found {} primes", executionTime, primes.size());
        
        PrimeNumberPrinter.printPrimes(primes.subList(0, Math.min(DISPLAY_LIMIT, primes.size())), 
                                       primes.size());
        
        return primes;
    }
}


