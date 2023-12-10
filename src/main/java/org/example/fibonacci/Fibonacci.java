package org.example.fibonacci;

// An example of the simplest algorithm for calculating Fibonacci numbers
public class Fibonacci {
    public static void main(String[] args) {
        int n = 15;
        int[] fibo = new int[n];
        fibo[0] = 0;
        fibo[1] = 1;
        for (int i = 2; i < n; i++) {
            fibo[i] = fibo[i - 1] + fibo[i - 2];
        }
        for (int i = 0; i < n; i++) {
            System.out.println(fibo[i]);
        }
    }
}