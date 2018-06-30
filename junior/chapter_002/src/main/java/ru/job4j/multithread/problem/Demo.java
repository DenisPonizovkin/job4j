package ru.job4j.multithread.problem;

/**
 * Demonstration of multithreading problems: race condition and visibility.
 */
public class Demo implements Runnable {

    private Series s;

    public Demo(Series s) {
        this.s = s;
    }

    @Override
    public void run() {
        while (!s.isReady()) {
            s.next();
            //try {
            //    Thread.sleep(100);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
    }
}
