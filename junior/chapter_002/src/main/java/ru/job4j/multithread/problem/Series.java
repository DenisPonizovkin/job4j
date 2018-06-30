package ru.job4j.multithread.problem;

public class Series {
    private long sum;
    private final long end;

    public Series(long end) {
        sum = 0;
        this.end = end;
    }

    public boolean isReady() {
        return sum == end;
    }

   public void next() {
        sum++;
   }

    @Override
    public String toString() {
        return "Sim is " + sum;
    }

    public long getRes() {
        return sum;
    }
}
