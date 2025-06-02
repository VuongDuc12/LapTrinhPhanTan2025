package org.example;

public class Main {
    public static void main(String[] args) {
        ReaderWriter rw = new ReaderWriter();

        // Tạo các thread người đọc
        for (int i = 1; i <= 3; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    rw.startRead(id);
                    Thread.sleep(1000); // Giả lập thời gian đọc
                    rw.endRead(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // Tạo các thread người ghi
        for (int i = 1; i <= 2; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    Thread.sleep(500); // Delay để cho reader chạy trước
                    rw.startWrite(id);
                    Thread.sleep(1500); // Giả lập thời gian ghi
                    rw.endWrite(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}