package com.example.course_api.concurrency;

import java.util.concurrent.*;

class ThreadExample1 extends Thread {
    private String threadName;
    
    public ThreadExample1(String threadName) {
        this.threadName = threadName;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(threadName + " - Iteración: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(threadName + " fue interrumpido");
            }
        }
    }
}

class RunnableExample implements Runnable {
    private String taskName;
    
    public RunnableExample(String taskName) {
        this.taskName = taskName;
    }
    
    @Override
    public void run() {
        System.out.println("Ejecutando tarea: " + taskName);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Tarea completada: " + taskName);
    }
}

class Counter {
    private int count = 0;
    
    public synchronized void increment() {
        count++;
        System.out.println("Count: " + count + " - Thread: " + Thread.currentThread().getName());
    }
    
    public synchronized int getCount() {
        return count;
    }
}

class VolatileExample {
    private volatile boolean running = true;
    
    public void stop() {
        running = false;
    }
    
    public void run() {
        while (running) {
            System.out.println("Ejecutando...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Detenido");
    }
}

class ExecutorServiceExample {
    public static void executeTasks() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Tarea " + taskId + " ejecutada por: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}

class CompletableFutureExample {
    public static void asyncExample() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                return "Resultado de operación asíncrona";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        
        future.thenAccept(result -> {
            System.out.println("Resultado: " + result);
        });
        
        System.out.println("Continuando con otras tareas...");
    }
}

class FinalVsFinallyExample {
    private final String constantValue = "Este valor no puede cambiar";
    private final int finalNumber = 42;
    
    public void demonstrateFinal() {
        System.out.println("Valor final: " + finalNumber);
    }
    
    public void demonstrateFinally() {
        try {
            int result = 10 / 2;
            System.out.println("Resultado: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Este código siempre se ejecuta (finally)");
        }
    }
    
    public void tryWithResources() {
        try (AutoCloseable resource = () -> System.out.println("Cerrando recurso")) {
            System.out.println("Usando recurso...");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
