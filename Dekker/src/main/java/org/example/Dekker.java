package org.example;

// Interface để định nghĩa Lock

// Cài đặt thuật toán Dekker
public class Dekker implements Lock {
    boolean[] wantCS = {false, false}; // Cờ muốn vào vùng quan trọng
    int turn = 0;                      // Ai được ưu tiên

    public void requestCS(int tid) {
        int other = 1 - tid;
        wantCS[tid] = true;

        while (wantCS[other]) {
            if (turn == other) {
                wantCS[tid] = false;
                while (turn == other) {
                    // chờ đến lượt
                }
                wantCS[tid] = true;
            }
        }
    }

    public void releaseCS(int tid) {
        turn = 1 - tid;
        wantCS[tid] = false;
    }
}

