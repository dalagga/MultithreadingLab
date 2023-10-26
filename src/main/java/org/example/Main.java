package org.example;

public class Main {
    static final Object countSync = new Object();
    static int iterations = 100_000;
    static int counter = 0;

    public static void main(String[] args) {
        MyTask threadThr = new MyTask();
        MyAnotherTask runn = new MyAnotherTask();
        Thread threadRunn = new Thread(runn);
        Thread threadLamb = new Thread(() -> {
            for (int i = 0; i < iterations; i++){
                synchronized (countSync){
                    counter += 1;}}
        });
        threadThr.start();
        threadRunn.start();
        threadLamb.start();

        try {
            threadThr.join();
            threadRunn.join();
            threadLamb.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
    }

    static class MyTask extends Thread {
        public void run() {
            for (int i = 0; i < iterations; i++){
                synchronized (countSync){
                    counter += 1;}}
        }
    }

    static class MyAnotherTask implements Runnable {
        public void run() {
            for (int i = 0; i < iterations; i++){
                synchronized (countSync){
                    counter += 1;}}
        }
    }
}
