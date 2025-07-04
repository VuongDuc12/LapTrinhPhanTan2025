package org.example;

public class CountingSemaphore {
    private int count;

    public CountingSemaphore(int init) {
        count = init;
    }

    public synchronized void P() throws InterruptedException {
        while (count == 0) {
            wait();
        }
        count--;
    }

    public synchronized void V() {
        count++;
        notify();
    }
}
