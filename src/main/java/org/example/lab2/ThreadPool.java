package org.example.lab2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    public static void main (String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++){
            Task task = new Task("TaskNum: " + i);
            executor.submit(task);
        }
        executor.shutdown();
        try {
            executor.submit(()->
                    System.out.println("Hello from " + Thread.currentThread().getName() + " thread"));
        } catch (Exception e) {
            System.err.println("EXCEPTION: " + e.getClass().getSimpleName() + ": " + e.getMessage());

        }
    }
    static class Task implements Runnable{
        private String taskName;

        public Task(String taskName){
            this.taskName = taskName;
        }

        @Override
        public void run() {
            System.out.println("Executing: " + taskName + " by " + Thread.currentThread().getName() + " thread");
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(taskName + " finished" + " by " + Thread.currentThread().getName() + " thread");
        }
    }
}
