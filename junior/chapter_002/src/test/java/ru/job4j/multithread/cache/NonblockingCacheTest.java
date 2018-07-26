package ru.job4j.multithread.cache;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NonblockingCacheTest {
    class AsynchTester {
        private Thread thread;
        private AssertionError exc;

        public AsynchTester(NonblockingCache cache, Base b) {
            thread = new Thread(new TestThread(cache, b));
        }

        public void start() {
            thread.start();
        }

        public void test() throws InterruptedException {
            thread.join();
            if (exc != null) {
                System.out.println("??????????????????????????????????????????? " + exc.getMessage());
                if (exc.getMessage().contains("OptimisticException")) {
                    throw new NonblockingCache.OptimisticException();
                }
            }
        }
    }

    class TestThread implements Runnable {

        private final NonblockingCache cache;
        private final Base b;

        public TestThread(NonblockingCache cache, Base b) {
            this.cache = cache;
            this.b = b;
        }

        @Override
        public void run() {
            while (true) {
                cache.update(b);
            }
        }
    }

    private volatile boolean stopped = false;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = NonblockingCache.OptimisticException.class)
    public void whenDataIsNotActualThenThrowsException() throws InterruptedException {

        exception.expect(InterruptedException.class);
        exception.expect(NonblockingCache.OptimisticException.class);

        NonblockingCache cache = new NonblockingCache();
        Base b1 = new Base();
        cache.add(1, b1);

        AsynchTester t1 = new AsynchTester(cache, b1);
        AsynchTester t2 = new AsynchTester(cache, b1);

        try {
            t1.test();
            t2.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
