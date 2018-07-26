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
        private Exception exc = null;

        public AsynchTester(NonblockingCache cache, Base b, int musecs) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            cache.update(b);
                            Thread.sleep(musecs);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (NonblockingCache.OptimisticException e) {
                            System.out.println("////////////////////////////////////////");
                            e.printStackTrace();
                            System.out.println("////////////////////////////////////////");
                            exc = e;
                            break;
                        }
                    }
                }
            });
        }

        public void start() {
            thread.start();
        }

        public void test() throws Exception {
            thread.join();
            if (exc != null) {
               throw exc;
            }
        }
    }
    // May be use Thread.UncaughtExceptionHandler
    class TestThread implements Runnable {

        private final NonblockingCache cache;
        private final int musecs;
        private final Base b;

        public TestThread(NonblockingCache cache, Base b, int n) {
            this.cache = cache;
            this.b = b;
            this.musecs = n;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    cache.update(b);
                    Thread.sleep(musecs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("////////////////////////////////////////");
                    e.printStackTrace();
                    System.out.println("////////////////////////////////////////");
                    throw e;
                }
            }
        }
    }

    private volatile boolean stopped = false;

    @Test(expected = NonblockingCache.OptimisticException.class)
    public void whenDataIsNotActualThenThrowsException() throws Exception {

        NonblockingCache cache = new NonblockingCache();
        Base b = new Base();
        cache.add(1, b);

        AsynchTester t1 = new AsynchTester(cache, b, 10);
        AsynchTester t2 = new AsynchTester(cache, b, 10);
        t1.start();
        t2.start();
        t1.test();
        t2.test();

        //Thread t1 = new Thread(new TestThread(cache, b, 10));
        //Thread t2 = new Thread(new TestThread(cache, b, 10));

        //t1.start();
        //t2.start();

        //try {
        //    t1.join();
        //    t2.join();
        //} catch (InterruptedException e) {
        //    System.out.println("???????????????????????????????????????");
        //    e.printStackTrace();
        //}
    }

    //@Test
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
