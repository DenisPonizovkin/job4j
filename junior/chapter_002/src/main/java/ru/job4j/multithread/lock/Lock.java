package ru.job4j.multithread.lock;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Lock {

    @GuardedBy("this")
    private boolean locked = false;

    synchronized public void lock() {
        while (locked) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        locked = true;
    }

    synchronized public void unlock() {
        locked = false;
    }
}
