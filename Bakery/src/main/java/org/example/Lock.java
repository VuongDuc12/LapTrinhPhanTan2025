package org.example;

public interface Lock {
    void lock(int id);
    void unlock(int id);
}