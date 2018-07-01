package ru.job4j.multithread.problem;

/**
 * Demonstration of multithreading problems: race condition and visibility.
 */
public class RaceConditionDemo implements Runnable {

    private Accumulator acc;
    private final int n;

    public RaceConditionDemo(Accumulator acc, int n) {
        this.acc = acc;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            acc.setAcc(acc.getAcc() + 1);
        }
    }
}
