package org.example;

public class BoundedBuffer {
    final int size = 10;
    double[] buffer = new double[size];
    int inBuf = 0, outBuf = 0;

    BinarySemaphore mutex = new BinarySemaphore(true);
    CountingSemaphore isEmpty = new CountingSemaphore(0);
    CountingSemaphore isFull = new CountingSemaphore(size);

    public void deposit(double value) {
        isFull.P();   // chờ nếu đầy
        mutex.P();    // vào vùng tới hạn
        buffer[inBuf] = value;
        inBuf = (inBuf + 1) % size;
        mutex.V();    // rời vùng tới hạn
        isEmpty.V();  // báo cho consumer
    }

    public double fetch() {
        double value;
        isEmpty.P();  // chờ nếu rỗng
        mutex.P();    // vào vùng tới hạn
        value = buffer[outBuf];
        outBuf = (outBuf + 1) % size;
        mutex.V();    // rời vùng tới hạn
        isFull.V();   // báo cho producer
        return value;
    }

}