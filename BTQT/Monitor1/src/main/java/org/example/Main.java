package org.example;

import java.util.Random;

public class Main {

    // Monitor: BoundedBuffer
    static class BoundedBuffer {
        private final int[] buffer;
        private int count = 0, in = 0, out = 0;
        private final int size;

        public BoundedBuffer(int size) {
            this.size = size;
            buffer = new int[size];
        }

        public synchronized void deposit(int item) throws InterruptedException {
            while (count == size) {
                wait(); // buffer đầy
            }
            buffer[in] = item;
            in = (in + 1) % size;
            count++;
            notifyAll(); // báo cho consumer
        }

        public synchronized int fetch() throws InterruptedException {
            while (count == 0) {
                wait(); // buffer rỗng
            }
            int item = buffer[out];
            out = (out + 1) % size;
            count--;
            notifyAll(); // báo cho producer
            return item;
        }
    }

    // Producer Thread
    static class Producer implements Runnable {
        private final BoundedBuffer buffer;
        private final Random rand = new Random();

        public Producer(BoundedBuffer b) {
            buffer = b;
            new Thread(this).start();
        }

        public void run() {
            try {
                while (true) {
                    int item = rand.nextInt(1000);
                    buffer.deposit(item);
                    System.out.println("Produced: " + item);
                    Thread.sleep(200); // giả lập thời gian sản xuất
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Consumer Thread
    static class Consumer implements Runnable {
        private final BoundedBuffer buffer;

        public Consumer(BoundedBuffer b) {
            buffer = b;
            new Thread(this).start();
        }

        public void run() {
            try {
                while (true) {
                    int item = buffer.fetch();
                    System.out.println("Consumed: " + item);
                    Thread.sleep(300); // giả lập thời gian tiêu thụ
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer(5);

        // Khởi tạo producer và consumer
        new Producer(buffer);
        new Producer(buffer);
        new Consumer(buffer);
        new Consumer(buffer);
    }
}
