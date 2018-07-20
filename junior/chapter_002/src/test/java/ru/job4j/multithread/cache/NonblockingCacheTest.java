package ru.job4j.multithread.cache;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NonblockingCacheTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void whenDataIsNotActualThenThrowsException() throws InterruptedException {

        exception.expect(InterruptedException.class);
        exception.expect(NonblockingCache.OptimisticException.class);

        NonblockingCache cache = new NonblockingCache();
        Base b1 = new Base();
        cache.add(1, b1);

        Thread th1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    cache.update(b1);
                }
            }
        };
        th1.setName("thread 1");

        Thread th2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    cache.update(b1);
                }
            }
        };
        th2.setName("thread 2");

        th1.start();
        th2.start();
        th1.join();
        th2.join();
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
