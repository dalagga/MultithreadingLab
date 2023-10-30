package org.example;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count = 0;
    private Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
    public int getCount (){
        try {
            lock.lock();
            return count;
        } finally {
            lock.unlock();
        }
    }
}
public class Lab2ReentrantLock {
    public static void main (String[] args){
    Counter counter = new Counter();
    Thread thread1 = new Thread(() -> {
        for (int i = 0; i < 100_000; i++) {
            counter.increment();
        }
    });
    Thread thread2 = new Thread(() -> {
        for (int i = 0; i < 100_000; i++) {
            counter.increment();
        }
    });
    thread1.start();
    thread2.start();
    try {
        thread1.join();
        thread2.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.err.println(counter.getCount());
    }
}
