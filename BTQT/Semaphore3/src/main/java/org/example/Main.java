package org.example;

public class Main {
    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[DiningPhilosopher.NUM_PHILOSOPHERS];

        for (int i = 0; i < DiningPhilosopher.NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i);
            philosophers[i].start();
        }
    }
}
