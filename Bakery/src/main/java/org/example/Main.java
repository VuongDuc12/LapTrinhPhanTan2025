package org.example;

import org.example.Bakery;

import java.util.Scanner;

public class Main {

    static int[] arr;
    static int N, K;
    static int shared_max = Integer.MIN_VALUE;
    static int shared_min = Integer.MAX_VALUE;

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap so lượng phan tu N: ");
        N = sc.nextInt();

        arr = new int[N];
        System.out.println("Nhập " + N + " phần tử:");
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.print("Nhập số luồng K: ");
        K = sc.nextInt();

        Bakery lock = new Bakery(K);

        Thread[] threads = new Thread[K];

        int partSize = N / K;
        int remainder = N % K;

        int start = 0;

        for (int i = 0; i < K; i++) {
            int end = start + partSize + (i < remainder ? 1 : 0);
            int threadIndex = i;
            int s = start;
            int e = end;

            threads[i] = new Thread(() -> {
                int localMax = Integer.MIN_VALUE;
                int localMin = Integer.MAX_VALUE;

                for (int idx = s; idx < e; idx++) {
                    if (arr[idx] > localMax) localMax = arr[idx];
                    if (arr[idx] < localMin) localMin = arr[idx];
                }

                lock.lock(threadIndex);

                if (localMax > shared_max) shared_max = localMax;
                if (localMin < shared_min) shared_min = localMin;

                lock.unlock(threadIndex);
            });

            start = end;
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads){
            t.join();
        }


        System.out.println("Giá trị lớn nhất: " + shared_max);
        System.out.println("Giá trị nhỏ nhất: " + shared_min);
    }
}
