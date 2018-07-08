package ru.job4j.multithread.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private int max;
    private final Object lock = new Object();

    @GuardedBy("lock")
    private Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int max) {
        this.max = max;
    }

    public void offer(T value) {
        synchronized (lock) {
            while (queue.size() == max) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(value);
            try {
                if (queue.size() == 0) {
                    notify();
                }
            } catch (IllegalMonitorStateException e) {
                e.printStackTrace();
            }
        }
    }

    public T poll() {
        T val = null;
        synchronized (lock) {
            while (queue.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            val = queue.poll();
            try {
                if (queue.size() == max) {
                    notify();
                }
            } catch (IllegalMonitorStateException e) {
                e.printStackTrace();
            }

        }
        return val;
    }
}
