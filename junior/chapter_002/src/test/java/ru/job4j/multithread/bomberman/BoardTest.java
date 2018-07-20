package ru.job4j.multithread.bomberman;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BoardTest {

    @Test
    public void whenTwoThreadWorkingThenNoDeadlock() {

        Board board = new Board(2, 1);

        Cell c1 = new Cell(0, 0);
        Cell c2 = new Cell(1, 0);

        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        board.move(c1, c2);
                        Thread.sleep(10);
                        board.move(c2, c1);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        board.move(c2, c1);
                        Thread.sleep(10);
                        board.move(c1, c2);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(true, is(true));
    }

    @Test
    public void whenTwoThreadWorkingThenCellsIsLocked() {

        Board board = new Board(100, 100);

        Cell current1 = new Cell(0, 0);
        Cell current2 = new Cell(1, 0);
        Cell last1 = new Cell(0, 0);
        Cell last2 = new Cell(1, 0);

        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1; i++) {
                    Random rnd = new Random(System.currentTimeMillis());
                    Cell to = new Cell(rnd.nextInt(100), rnd.nextInt(100));
                    try {
                        board.move(current1, last1);

                        System.out.println("From " + current1 + " to " + last1);

                        Thread.sleep(10);
                        current1.setX(last1.getX());
                        current1.setY(last1.getY());
                        last1.setX(to.getX());
                        last1.setY(to.getY());

                        System.out.println("Moving success");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    Random rnd = new Random(System.currentTimeMillis());
                    Cell to = new Cell(rnd.nextInt(100), rnd.nextInt(100));
                    try {
                        board.move(current2, last2);
                        Thread.sleep(10);
                        current2.setX(last2.getX());
                        current2.setY(last2.getY());
                        last2.setX(to.getX());
                        last2.setY(to.getY());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(current1.getX(), is(last1.getX()));
        assertThat(current1.getY(), is(last1.getY()));
        assertThat(current2.getX(), is(last2.getX()));
        assertThat(current2.getY(), is(last2.getY()));
    }
}
