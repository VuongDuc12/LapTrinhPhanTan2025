package org.example;

import java.util.concurrent.Semaphore;

public class ReaderWriter {
    private int readCount = 0;
    private final Semaphore mutex = new Semaphore(1);      // Bảo vệ readCount
    private final Semaphore writeLock = new Semaphore(1);  // Bảo vệ tài nguyên ghi

    public void startRead(int id) throws InterruptedException {
        mutex.acquire();
        readCount++;
        if (readCount == 1) {
            writeLock.acquire(); // Người đọc đầu tiên khóa ghi
        }
        mutex.release();
        System.out.println("Reader " + id + " is reading...");
    }

    public void endRead(int id) throws InterruptedException {
        mutex.acquire();
        readCount--;
        if (readCount == 0) {
            writeLock.release(); // Người đọc cuối cùng mở khóa ghi
        }
        mutex.release();
        System.out.println("Reader " + id + " finished reading.");
    }

    public void startWrite(int id) throws InterruptedException {
        writeLock.acquire();
        System.out.println("Writer " + id + " is writing...");
    }

    public void endWrite(int id) {
        System.out.println("Writer " + id + " finished writing.");
        writeLock.release();
    }
}