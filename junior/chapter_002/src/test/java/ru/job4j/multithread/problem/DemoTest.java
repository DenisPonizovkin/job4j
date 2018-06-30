package ru.job4j.multithread.problem;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DemoTest {

    @Test
    public void whenMultipleThreadsRaceConditionExists() {

        final int n = 5;
        final long res = n * 10000000;

        Thread[] threads = new Thread[n];
        Series s = new Series(res);
        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(new Demo(s));
        }
        for (int i = 0; i < n; i++) {
            threads[i].start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < n; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(s);
        assertThat(s.getRes() == res, is(true));
    }

}