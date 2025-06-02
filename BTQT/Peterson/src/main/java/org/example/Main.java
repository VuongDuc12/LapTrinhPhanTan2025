package org.example;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Main{

    static int x = 0;
    static lock lock = new Peterson(); // Khởi tạo Peterson

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                lock.requestCS(0); // pid = 0
                x = x + 1;
                lock.releaseCS(0);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                lock.requestCS(1); // pid = 1
                x = x + 1;
                lock.releaseCS(1);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Giá trị x cuối cùng: " + x);
    }
}