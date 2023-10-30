package org.example;

import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class Lab2CountDownLatch{

    private static class Task implements Runnable{
        private final CountDownLatch latch;
        public Task (CountDownLatch latch){
            this.latch = latch;
        }
        @Override
        public void run() {
            System.err.println("waiting for latch");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("latch released");
        }}

        public static void main(String[] args){
            CountDownLatch latch = new CountDownLatch(1);
            for (int i = 0; i < 6; i++){
                Thread task = new Thread(new Task(latch));
                task.start();
            }
            try {
                Thread.sleep(3000);
                System.err.println("releasing latch");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
    }
}