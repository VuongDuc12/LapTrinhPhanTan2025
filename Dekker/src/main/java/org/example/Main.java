package org.example;

import java.util.Scanner;

class Worker extends Thread {
    private int tid;
    private Lock lock;
    private int[] array;
    private int start, end;

    // Biến chia sẻ
    public static int shared_max = Integer.MIN_VALUE;
    public static int shared_min = Integer.MAX_VALUE;

    public Worker(int tid, Lock lock, int[] array, int start, int end) {
        this.tid = tid;
        this.lock = lock;
        this.array = array;
        this.start = start;
        this.end = end;
    }

    private void CS() {
        for (int i = start; i <= end; i++) {
            if (array[i] > shared_max) {
                shared_max = array[i];
            }
            if (array[i] < shared_min) {
                shared_min = array[i];
            }
        }
        System.out.println("Thread " + tid + " hoàn thành cùng tới hạn.");
    }

    private void nonCS() {
        System.out.println("Thread " + tid + " Xử lý xong vùng không quan trọng.");
    }

    public void run() {
        lock.requestCS(tid);
        CS();
        lock.releaseCS(tid);
        nonCS();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập N: ");
        int N = sc.nextInt();
        int[] array = new int[N];
        System.out.println("Nhập " + N + " phần tử:");
        for (int i = 0; i < N; i++) {
            array[i] = sc.nextInt();
        }
        Lock lock = new Dekker();

        int mid = N / 2;
        Worker t0 = new Worker(0, lock, array, 0, mid - 1);
        Worker t1 = new Worker(1, lock, array, mid, N - 1);

        t0.start();
        t1.start();

        try {
            t0.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nGiá trị lớn nhất (max): " + Worker.shared_max);
        System.out.println("Giá trị nhỏ nhất (min): " + Worker.shared_min);
    }
}