package org.example.lab2;

public class Semaphore {
    public static void main(String[] args) {
        final int numThreads = 3;
        final java.util.concurrent.Semaphore semaphore = new java.util.concurrent.Semaphore(numThreads);
        for (int i = 0; i < 9; i++) {
            Thread thread = new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("Thread " + Thread.currentThread().getName() + " is working");
                    Thread.sleep(2000);
                    System.out.println("Thread " + Thread.currentThread().getName() + " has finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
            thread.start();
    }
}}
