package ru.job4j.multithread.queue;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    public class Consumer implements Runnable {
        SimpleBlockingQueue<Integer> queue;
        private final int n;
        private int count = 0;

        public Consumer(int n, SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
            this.n = n;
        }

        @Override
        public void run() {
            for (count = 0; count < n; count++) {
                queue.poll();
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
        SimpleBlockingQueue<Integer> queue;
        private final int n;
        private int count = 0;

        public Producer(int n, SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
            this.n = n;
        }

        @Override
        public void run() {
            for (count = 0; count < n; count++) {
                Random rnd = new Random(System.currentTimeMillis());
                queue.offer(rnd.nextInt());
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
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue(5);
        Producer p = new Producer(10, sbq);
        Consumer c = new Consumer(10, sbq);
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

