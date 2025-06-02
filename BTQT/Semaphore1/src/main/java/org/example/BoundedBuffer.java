package org.example;

public class BoundedBuffer {
    private final int[] buffer;
    private int in = 0, out = 0;
    private final int size;

    private final CountingSemaphore empty;
    private final CountingSemaphore full;
    private final BinarySemaphore mutex;

    public BoundedBuffer(int size) {
        this.size = size;
        buffer = new int[size];
        empty = new CountingSemaphore(size); // buffer ban đầu trống
        full = new CountingSemaphore(0);     // chưa có phần tử nào
        mutex = new BinarySemaphore(true);   // cho phép truy cập
    }

    public void produce(int item) throws InterruptedException {
        empty.P(); // chờ có chỗ trống
        mutex.P(); // đảm bảo độc quyền

        buffer[in] = item;
        System.out.println("Produced: " + item);
        in = (in + 1) % size;

        mutex.V(); // giải phóng
        full.V();  // tăng số phần tử
    }

    public int consume() throws InterruptedException {
        full.P(); // chờ có dữ liệu
        mutex.P(); // đảm bảo độc quyền

        int item = buffer[out];
        System.out.println("Consumed: " + item);
        out = (out + 1) % size;

        mutex.V(); // giải phóng
        empty.V(); // tăng chỗ trống

        return item;
    }
}