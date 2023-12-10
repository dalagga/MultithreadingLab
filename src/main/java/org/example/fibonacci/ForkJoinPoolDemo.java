package org.example.fibonacci;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

// Define a RecursiveTask to compute the sum of an array of integers
class SumTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 25; // Threshold for dividing tasks
    private int[] array;
    private int start;
    private int end;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        // If the task is small enough, compute the sum directly
        if (end - start <= THRESHOLD) {
            System.err.println("Computing sum from " + start + " to " + end);
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Split the task into smaller sub-tasks
            int mid = (start + end) / 2;
            System.err.println("Splitting task from " + start + " to " + end + " into " + start + " to " + mid + " and " + mid + " to " + end);
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);

            // Fork the sub-tasks and wait for their results
            leftTask.fork();
            int rightResult = rightTask.compute();

            // Join the results of sub-tasks
            int leftResult = leftTask.join();

            // Combine the results and return
            System.err.println("Joining results from " + start + " to " + mid + " and " + mid + " to " + end);
            return leftResult + rightResult;
        }
    }
}

public class ForkJoinPoolDemo {
    public static void main(String[] args) {
        int[] array = new int[102]; // Example array of integers

        // Fill the array with random values (for demonstration purposes)
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 100);
        }

        // Create a ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // Create a task to compute the sum of the array
        SumTask sumTask = new SumTask(array, 0, array.length);

        // Invoke the task within the ForkJoinPool
        int result = forkJoinPool.invoke(sumTask);

        // Print the result
        System.out.println("Sum: " + result);
    }
}