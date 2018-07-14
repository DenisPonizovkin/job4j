package ru.job4j.multithread.cache;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NonblockingCacheTest {

    @Test
    public void whenDataIsNotActualThenThrowsException() {

        NonblockingCache cache = new NonblockingCache();
        Base b1 = new Base();
        cache.add(1, b1);

        Thread slow = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cache.update(b1);
                }
            }
        };
        slow.setName("Slow thread");

        Thread fast = new Thread() {
            @Override
            public void run() {
                while (true) {
                    cache.update(b1);
                }
            }
        };
        fast.setName("Fast thread");

        slow.start();
        fast.start();
        boolean ok = false;
        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(100);
                if ((!slow.isAlive()) || (!fast.isAlive())) {
                    ok = true;
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(ok, is(true));
    }

    @Test
    public void whenPutNElementsThenSizeIsN() {

        NonblockingCache cache = new NonblockingCache();
        final int nTh = 10;
        final int n = 10;
        List<Thread> threads = new ArrayList<Thread>();
        final int[] id = {0};
        for (int i = 0; i < nTh; i++) {
            threads.add(
                    new Thread() {
                        @Override
                        public void run() {
                            for (int i = 0; i < n; i++) {
                                cache.add(id[0]++, new Base());
                            }
                        }
                    }
            );
        }
        for (int i = 0; i < nTh; i++) {
            threads.get(i).start();
        }
        for (int i = 0; i < nTh; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertThat(cache.size(), is(nTh * n));
    }
}
