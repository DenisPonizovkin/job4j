package ru.job4j.multithread.containers.list.linked;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DynamicArrayTest {

    private boolean wasEx = false;

    public class DynamicArrayThreadTest implements Runnable {

        private DynamicArray<Integer> data;
        boolean increment;

        public DynamicArrayThreadTest(DynamicArray<Integer> data, boolean inc) {
            this.data = data;
            this.increment = inc;
        }

        @Override
        public void run() {
            synchronized (data) {
                if (increment) {
                    for (int i = 0; i < 100; i++) {
                        Integer n = data.get(0);
                        data.set(0, n + 1);
                    }
                } else {
                    data.remove(0);
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
        DynamicArray<Integer> data = new DynamicArray<Integer>();
        data.add(0);

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new DynamicArrayThreadTest(data, true));
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
        DynamicArray<Integer> data = new DynamicArray<Integer>();
        data.add(0);
        final int n = 2;
        ExHandler handler = new ExHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);

        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(new DynamicArrayThreadTest(data, false));
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