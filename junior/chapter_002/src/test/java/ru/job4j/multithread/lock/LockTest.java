package ru.job4j.multithread.lock;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LockTest {
    public class Consumer implements Runnable {
        private final Queue<Integer> queue;
        private final int n;
        private final Lock lock;
        private int count = 0;

        public Consumer(int n, Queue<Integer> queue, Lock lock) {
            this.queue = queue;
            this.n = n;
            this.lock = lock;
        }

        @Override
        public void run() {
            for (count = 0; count < n; count++) {
                lock.lock();
                queue.poll();
                lock.unlock();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public int getCount() {
            return count;
        }
    }

    public class Producer implements Runnable {
        private final Queue<Integer> queue;
        private final Lock lock;
        private final int n;
        private int count = 0;

        public Producer(int n, Queue<Integer> queue, Lock lock) {
            this.queue = queue;
            this.n = n;
            this.lock = lock;
        }

        @Override
        public void run() {
            for (count = 0; count < n; count++) {
                Random rnd = new Random(System.currentTimeMillis());
                lock.lock();
                queue.offer(rnd.nextInt());
                lock.unlock();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public int getCount() {
            return count;
        }
    }

    @Test
    public void whenOfferNElementsThenPollNElements() {
        Queue<Integer> sbq = new LinkedList<Integer>();
        Lock lock = new Lock();
        Producer p = new Producer(10, sbq, lock);
        Consumer c = new Consumer(10, sbq, lock);
        Thread pThread = new Thread(p);
        Thread cThread = new Thread(c);
        pThread.start();
        cThread.start();
        try {
            pThread.join();
            cThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(p.getCount(), is(c.getCount()));
    }
}
