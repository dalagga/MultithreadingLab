package org.example.fibonacci;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/* Java program that uses
the ForkJoinPool and RecursiveTask
to compute the Fibonacci number
at a specific index in a multithreaded manner. */

class FibonacciTask extends RecursiveTask<Integer> {
    final int n;
    FibonacciTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }
        FibonacciTask f1 = new FibonacciTask(n - 1);
        FibonacciTask f2 = new FibonacciTask(n - 2);
        f1.fork();
        return f2.compute() + f1.join();
    }
}

public class FibonacciWithForkJoin {
    public static void main(String[] args) {
        int n = 10; // index of Fibonacci number
        ForkJoinPool pool = new ForkJoinPool();
        FibonacciTask task = new FibonacciTask(n);
        int result = pool.invoke(task);
        System.out.println(result);
    }
}
