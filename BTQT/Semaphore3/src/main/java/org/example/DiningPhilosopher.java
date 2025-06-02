package org.example;

import java.util.concurrent.Semaphore;

public class DiningPhilosopher {
    public static final int NUM_PHILOSOPHERS = 5;
    public static Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];
    public static Semaphore mutex = new Semaphore(1); // tránh deadlock

    static {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1); // mỗi đũa là semaphore 1
        }
    }
}
