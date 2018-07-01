package ru.job4j.multithread.problem;

public class VisibilityDemo implements Runnable {

    private int thread1Value = 0;
    private int thread2Value = 0;

    @Override
    public void run() {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (thread1Value != thread2Value) {
                    thread1Value = thread2Value;
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                thread2Value++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int getThread1Value() {
        return thread1Value;
    }

    public int getThread2Value() {
        return thread2Value;
    }
}
