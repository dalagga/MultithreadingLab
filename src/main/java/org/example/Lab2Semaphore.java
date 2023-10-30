package org.example;

import java.util.concurrent.Semaphore;

public class Lab2Semaphore {
    public static void main(String[] args) {
        final int numThreads = 3;
        final Semaphore semaphore = new Semaphore(numThreads);
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
