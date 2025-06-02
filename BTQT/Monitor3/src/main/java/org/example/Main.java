package org.example;

public class Main {

    static final int N = 5;

    static final int THINKING = 0;
    static final int HUNGRY = 1;
    static final int EATING = 2;

    static class Monitor {
        private final int[] state = new int[N];

        public synchronized void takeForks(int i) {
            state[i] = HUNGRY;
            test(i);
            while (state[i] != EATING) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        public synchronized void putForks(int i) {
            state[i] = THINKING;
            test((i + N - 1) % N);
            test((i + 1) % N);
        }

        private void test(int i) {
            if (state[i] == HUNGRY &&
                    state[(i + N - 1) % N] != EATING &&
                    state[(i + 1) % N] != EATING) {
                state[i] = EATING;
                notifyAll();
            }
        }
    }

    static class Philosopher extends Thread {
        private final int id;
        private final Monitor monitor;

        public Philosopher(int id, Monitor monitor) {
            this.id = id;
            this.monitor = monitor;
        }

        public void run() {
            try {
                while (true) {
                    System.out.println("Philosopher " + id + " is THINKING");
                    Thread.sleep((int)(Math.random() * 300 + 200));

                    System.out.println("Philosopher " + id + " is HUNGRY");
                    monitor.takeForks(id);

                    System.out.println("Philosopher " + id + " is EATING");
                    Thread.sleep((int)(Math.random() * 300 + 200));

                    monitor.putForks(id);
                }
            } catch (InterruptedException e) {
                System.out.println("Philosopher " + id + " was interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        for (int i = 0; i < N; i++) {
            new Philosopher(i, monitor).start();
        }
    }
}
