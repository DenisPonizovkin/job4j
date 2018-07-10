package ru.job4j.multithread.pool;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ThreadPoolTest {
    public class BeepThread implements Runnable {

        private final String name;

        public BeepThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("beep " + name);
        }
    }

    @Test
    public void test() {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 1; i++) {
            pool.work(new BeepThread("" + i));
        }
        pool.shutdown();
        assertThat(pool.tasksNumber(), is(0));
    }

}