package ru.job4j.multithread.pool;

import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final Queue<Runnable> tasks = new LinkedBlockingQueue<>();

    @ThreadSafe
    public class PoolThread implements Runnable {
        private final Queue<Runnable> tasks;

        public PoolThread(Queue<Runnable> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (tasks) {
                    while (tasks.size() == 0) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Thread t = new Thread(tasks.peek());
                    t.start();
                    try {
                        t.join();
                        tasks.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(new PoolThread(tasks)));
        }
        for (int i = 0; i < size; i++) {
            threads.get(i).start();
        }
    }

    public void work(Runnable job) {
        synchronized (tasks) {
            tasks.add(job);
            try {
                tasks.notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        for (int i = 0; i < size; i++) {
            threads.get(i).interrupt();
            i++;
        }
    }

    public int tasksNumber() {
        synchronized (tasks) {
            return tasks.size();
        }
    }
}


