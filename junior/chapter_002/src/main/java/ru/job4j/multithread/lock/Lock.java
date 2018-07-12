package ru.job4j.multithread.lock;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Lock {

    @GuardedBy("this")
    private boolean locked = false;

     public synchronized void lock() {
        while (locked) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        locked = true;
    }

     public synchronized void unlock() {
        locked = false;
    }
}
