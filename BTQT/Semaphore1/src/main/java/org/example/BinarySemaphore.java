package org.example;

public class BinarySemaphore {
    private boolean value;

    public BinarySemaphore(boolean init) {
        value = init;
    }

    public synchronized void P() throws InterruptedException {
        while (!value) {
            wait();
        }
        value = false;
    }

    public synchronized void V() {
        value = true;
        notify();
    }
}