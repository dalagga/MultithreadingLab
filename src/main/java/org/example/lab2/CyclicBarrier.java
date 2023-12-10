package org.example.lab2;

class Worker implements Runnable{
    private java.util.concurrent.CyclicBarrier barrier;
    private String name;
    public Worker(java.util.concurrent.CyclicBarrier barrier, String name) {
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " is working");
        try {
            Thread.sleep((long)(Math.random() * 4000));
            System.out.println(name + " has finished and waiting at the barrier");
            barrier.await();
            System.out.println(name + " has passed the barrier");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class CyclicBarrier {
    public static void main (String[] args){
        int numWorkers = 3;
        java.util.concurrent.CyclicBarrier barrier = new java.util.concurrent.CyclicBarrier(numWorkers, () -> {
            System.out.println("All workers have reached the barrier");
        });
        for (int i = 0; i < numWorkers; i++){
            Thread worker = new Thread(new Worker(barrier, "Worker " + i));
            worker.start();
        }
    }
}
