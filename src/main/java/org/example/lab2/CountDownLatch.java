package org.example.lab2;

public class CountDownLatch {

    private static class Task implements Runnable{
        private final java.util.concurrent.CountDownLatch latch;
        public Task (java.util.concurrent.CountDownLatch latch){
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
            java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1);
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