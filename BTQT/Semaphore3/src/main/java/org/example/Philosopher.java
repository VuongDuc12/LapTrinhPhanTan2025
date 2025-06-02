package org.example;

public class Philosopher extends Thread {
    private final int id;

    public Philosopher(int id) {
        this.id = id;
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking...");
        Thread.sleep((int)(Math.random() * 1000));
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating...");
        Thread.sleep((int)(Math.random() * 1000));
    }

    public void run() {
        try {
            while (true) {
                think();

                // Giảm khả năng deadlock: dùng mutex để kiểm soát lấy đũa
                DiningPhilosopher.mutex.acquire();

                DiningPhilosopher.forks[id].acquire(); // lấy đũa trái
                DiningPhilosopher.forks[(id + 1) % DiningPhilosopher.NUM_PHILOSOPHERS].acquire(); // lấy đũa phải

                DiningPhilosopher.mutex.release();

                eat();

                // Trả đũa
                DiningPhilosopher.forks[id].release();
                DiningPhilosopher.forks[(id + 1) % DiningPhilosopher.NUM_PHILOSOPHERS].release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}