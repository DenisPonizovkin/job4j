package ru.job4j.multithread.problem;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RaceConditionDemoTest {

    @Test
    public void whenMultipleThreadsRaceConditionExists() {

        final int nThreads = 50;
        final int nAcc = 10000;
        final long res = nThreads * nAcc;

        Thread[] threads = new Thread[nThreads];
        Accumulator acc = new Accumulator();
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new Thread(new RaceConditionDemo(acc, nAcc));
        }
        for (int i = 0; i < nThreads; i++) {
            threads[i].start();
        }
        for (int i = 0; i < nThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Expected res: " + res + ", calculated: " + acc.getAcc());
        assertThat(acc.getAcc() == res, is(false));
    }

}