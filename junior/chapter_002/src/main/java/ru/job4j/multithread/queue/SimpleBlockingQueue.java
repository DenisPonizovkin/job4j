package ru.job4j.multithread.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final int max;
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int max) {
        this.max = max;
    }

    synchronized public void offer(T value) {
        while (queue.size() == max) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            notify();
            queue.offer(value);
        } catch (IllegalMonitorStateException e) {
            e.printStackTrace();
        }
    }

    synchronized public T poll() {
        T val = null;
        while (queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            notify();
            val = queue.poll();
        } catch (IllegalMonitorStateException e) {
            e.printStackTrace();
        }
        return val;
    }
}
