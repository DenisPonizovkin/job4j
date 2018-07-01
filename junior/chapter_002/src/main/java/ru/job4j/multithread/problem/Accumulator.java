package ru.job4j.multithread.problem;

public class Accumulator {

    private long acc;

    public Accumulator() {
        acc = 0;
    }

    public long getAcc() {
        return acc;
    }

    public void setAcc(long acc) {
        this.acc = acc;
    }
}
