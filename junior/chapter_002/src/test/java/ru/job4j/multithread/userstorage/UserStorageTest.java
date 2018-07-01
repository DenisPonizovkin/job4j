package ru.job4j.multithread.userstorage;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    public class StorageTest implements Runnable {

        private final UserStorage store;
        boolean transfer;

        public StorageTest(UserStorage store, boolean transfer) {
            this.store = store;
            this.transfer = transfer;
        }

        @Override
        public void run() {
            if (transfer) {
                store.transfer(1, 2, 100);
            } else {
                synchronized (store) {
                    User old = store.getUser(1);
                    User u1 = new User(1, old.getAmount() + 100);
                    store.update(u1);
                }
            }
        }
    }

    @Test
    public void whenMultipleThreadsThenTransferIsRight() {
        UserStorage store = new UserStorage();
        store.add(new User(1, 1000));
        store.add(new User(2, 0));
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new StorageTest(store, true));
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertThat(store.getUser(1).getAmount(), is(0));
        assertThat(store.getUser(2).getAmount(), is(1000));
    }

    @Test
    public void whenMultipleThreadsThenUpdateIsRight() {
        UserStorage store = new UserStorage();
        store.add(new User(1, 0));
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new StorageTest(store, false));
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertThat(store.getUser(1).getAmount(), is(1000));
    }

}