package org.example;


public class Bakery implements Lock {
    private final int n;
    private final boolean[] choosing;
    private final int[] ticket;

    public Bakery(int n) {
        this.n = n;
        choosing = new boolean[n];
        ticket = new int[n];
        for (int i = 0; i < n; i++) {
            choosing[i] = false;
            ticket[i] = 0;
        }
    }

    @Override
    public void lock(int id) {
        choosing[id] = true;
        ticket[id] = 1 + maxTicket();
        choosing[id] = false;

        for (int j = 0; j < n; j++) {
            if (j == id) continue;
            while (choosing[j]) Thread.yield();
            while (ticket[j] != 0 &&
                    (ticket[j] < ticket[id] || (ticket[j] == ticket[id] && j < id))) {
                Thread.yield();
            }
        }
    }

    @Override
    public void unlock(int id) {
        ticket[id] = 0;
    }

    private int maxTicket() {
        int max = 0;
        for (int t : ticket) {
            if (t > max) max = t;
        }
        return max;
    }
}
