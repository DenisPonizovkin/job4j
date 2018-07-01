package ru.job4j.multithread.containers.list.linked;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LinkedListArrayBasedTest {
    public class LinkedListArrayBasedThreadTest implements Runnable {

        private LinkedListArrayBased<Integer> data;
        boolean increment;

        public LinkedListArrayBasedThreadTest(LinkedListArrayBased<Integer> data, boolean inc) {
            this.data = data;
            this.increment = inc;
        }

        @Override
        public void run() {
            synchronized (data) {
                if (increment) {
                    for (int i = 0; i < 100; i++) {
                        Integer n = data.get(0);
                        data.update(0, n + 1);
                    }
                } else {
                    data.remove(new Integer(1));
                }
            }
        }
    }
    public class ExHandler implements Thread.UncaughtExceptionHandler {

        boolean was = false;

        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            was = true;
        }

        public boolean wasEx() {
            return was;
        }
    }

    @Test
    public void whenMultipleThreadsThenUpdateIsRight() {
        LinkedListArrayBased<Integer> data = new LinkedListArrayBased<Integer>();
        data.add(0);
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new LinkedListArrayBasedThreadTest(data, true));
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertThat(data.get(0), is(1000));
    }

    @Test
    public void whenMultipleThreadsRemoveSameElementThenThrowException() {
        LinkedListArrayBased<Integer> data = new LinkedListArrayBased<Integer>();
        data.add(new Integer(0));
        data.add(new Integer(1));
        data.add(new Integer(2));
        final int n = 2;
        ExHandler handler = new ExHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);

        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(new LinkedListArrayBasedThreadTest(data, false));
        }
        test(threads);
        assertThat(handler.wasEx(), is(true));
    }

    private void test(Thread[] threads) throws NoSuchElementException {
        try {
            for (int i = 0; i < threads.length; i++) {
                threads[i].start();
            }

            for (int i = 0; i < threads.length; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}