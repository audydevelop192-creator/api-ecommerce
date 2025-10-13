package com.ecommerce.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;

//@SpringBootTest
class TokoGymApplicationTests {

    public static void main(String[] args) {
        int n = 100000000; // ukuran input, bisa ubah untuk uji coba

        System.out.println("=== DEMO TIME COMPLEXITY ===");
        System.out.println("Input size: " + n + "\n");

        // O(1)
        measureTime("O(1) - Constant Time", () -> constantTime(n));

        // O(log n)
        measureTime("O(log n) - Logarithmic Time", () -> logarithmicTime(n));

        // O(n)
        measureTime("O(n) - Linear Time", () -> linearTime(n));

        // O(n log n)
        measureTime("O(n log n) - Linearithmic Time", () -> linearithmicTime(n));

        // O(n^2)
        measureTime("O(n^2) - Quadratic Time", () -> quadraticTime(n / 100)); // kecilin biar gak lama

        // O(2^n)
        measureTime("O(2^n) - Exponential Time (n=20)", () -> exponentialTime(20));
    }

    // Helper untuk mengukur waktu eksekusi
    public static void measureTime(String title, Runnable task) {
        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();

        long durationNs = end - start;
        double durationMs = durationNs / 1_000_000.0;

        System.out.printf("%-40s | %10d ns | %8.3f ms\n", title, durationNs, durationMs);
    }

    // ==== Contoh kasus untuk setiap kompleksitas ====

    // O(1)
    public static void constantTime(int n) {
        int result = n * n; // hanya 1 operasi
    }

    // O(log n)
    public static void logarithmicTime(int n) {
        int count = 0;
        while (n > 1) {
            n = n / 2; // tiap iterasi bagi dua
            count++;
        }
    }

    // O(n)
    public static void linearTime(int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
    }

    // O(n log n)
    public static void linearithmicTime(int n) {
        for (int i = 0; i < n; i++) {
            int m = n;
            while (m > 1) {
                m = m / 2;
            }
        }
    }

    // O(n²)
    public static void quadraticTime(int n) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                count++;
            }
        }
    }

    // O(2ⁿ) — rekursi Fibonacci
    public static int exponentialTime(int n) {
        if (n <= 1) return n;
        return exponentialTime(n - 1) + exponentialTime(n - 2);
    }

}
