package org.example;

public class Main {
    public static void main(String[] args) {
        ReaderWriterMonitor monitor = new ReaderWriterMonitor();

        for (int i = 0; i < 3; i++) {
            new Reader(i, monitor).start();
        }

        for (int i = 0; i < 2; i++) {
            new Writer(i, monitor).start();
        }
    }
}

// Monitor quản lý truy cập đọc/ghi
class ReaderWriterMonitor {
    private int readers = 0;
    private boolean writing = false;

    public synchronized void startRead(int id) throws InterruptedException {
        while (writing) {
            wait();
        }
        readers++;
        System.out.println("Reader " + id + " starts reading. Readers = " + readers);
    }

    public synchronized void endRead(int id) {
        readers--;
        System.out.println("Reader " + id + " ends reading. Readers = " + readers);
        if (readers == 0) {
            notifyAll();
        }
    }

    public synchronized void startWrite(int id) throws InterruptedException {
        while (readers > 0 || writing) {
            wait();
        }
        writing = true;
        System.out.println("Writer " + id + " starts writing.");
    }

    public synchronized void endWrite(int id) {
        writing = false;
        System.out.println("Writer " + id + " ends writing.");
        notifyAll();
    }
}

// Luồng người đọc
class Reader extends Thread {
    private int id;
    private ReaderWriterMonitor monitor;

    public Reader(int id, ReaderWriterMonitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    public void run() {
        try {
            while (true) {
                monitor.startRead(id);
                Thread.sleep(500); // thời gian đọc
                monitor.endRead(id);
                Thread.sleep(1000); // nghỉ
            }
        } catch (InterruptedException e) {
            System.out.println("Reader " + id + " interrupted.");
        }
    }
}

// Luồng người ghi
class Writer extends Thread {
    private int id;
    private ReaderWriterMonitor monitor;

    public Writer(int id, ReaderWriterMonitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    public void run() {
        try {
            while (true) {
                monitor.startWrite(id);
                Thread.sleep(1000); // thời gian ghi
                monitor.endWrite(id);
                Thread.sleep(1500); // nghỉ
            }
        } catch (InterruptedException e) {
            System.out.println("Writer " + id + " interrupted.");
        }
    }
}