package ru.job4j.multithread.bomberman;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BoardTest {

    class MonsterThread implements Runnable {

        private Monster m = null; // If it will be final then standOn will be executed in main thread
        // and cell will be locked by main thread and not current.
        private final Board board;
        private AssertionError err = null;
        private Cell start = null;

        MonsterThread(Board board, Cell start) {
            this.board = board;
            this.start = start;
        }

        MonsterThread(Board board) {
            this.board = board;
        }

        public Monster getMonster() {
            return m;
        }

        public Board getBoard() {
            return board;
        }

        @Override
        public void run() {
            if (start == null) {
                do {
                    Random rnd = new Random();
                    int wLimit = board.getWidthLimit();
                    int hLimit = board.getHeightLimit();
                    int x = rnd.nextInt(wLimit);
                    int y = rnd.nextInt(hLimit);
                    start = new Cell(x, y);
                    try {
                        m = new Monster(board, start);
                        m.init();
                        break;
                    } catch (Exception e) {
                        continue;
                    }
                } while (true);
            }
            for (int i = 0; i < 50; i++) {
                try {
                    m.randomMoving();
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                    assertThat(true, is(false));
                }
            }
        }
    }


    class MonsterMovesAlwaysRight extends MonsterThread {

        MonsterMovesAlwaysRight(Board board) throws InterruptedException {
            super(board);
            for (int i = 0; i < 10; i++) {
                Cell block = new Cell(5, i);
                try {
                    board.addBlock(block);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    assertThat(true, is(false));
                }
            }

        }

        @Override
        public void run() {
            Monster m = new Monster(getBoard(), new Cell(0, 4));
            try {
                m.init();
            } catch (InterruptedException e) {
                e.printStackTrace();
                assertThat(true, is((false)));
            }
            for (int i = 0; i < 30; i++) {
                try {
                    m.right();
                    System.out.println("==================> Cell " + i + ": " + m.getCurrentCell());
                    assertThat(m.getCurrentCell().getX() >= 5, is(false));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    assertThat(true, is(false));
                }
            }
        }
    }

    class AsynchTesterMonsterMovesRight {
        private Thread thread;
        private AssertionError err = null;

        // https://stackoverflow.com/questions/2596493/junit-assert-in-thread-throws-exception/2596530#2596530
        public AsynchTesterMonsterMovesRight(Runnable runnable) throws InterruptedException {

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runnable.run();
                    } catch (AssertionError e) {
                        err = e;
                    }
                }
            });
        }

        public void start() {
            thread.start();
        }

        public void test() throws Exception {
            thread.join();
            if (err != null) {
                throw err;
            }
        }
    }

    //@Test
    public void whenVerticalWallOnBoardThenMonsterCantGoOverIt() throws Exception {
        Board board = new Board(10, 10);
        MonsterMovesAlwaysRight runnable = new MonsterMovesAlwaysRight(board);
        AsynchTesterMonsterMovesRight t = new AsynchTesterMonsterMovesRight(runnable);
        t.start();
        t.test();
    }

    @Test
    public void whenMonstersMoveThenNoExceptions() throws Exception {
        Board board = new Board(30, 30);
        AsynchTesterMonsterMovesRight[] tests = new AsynchTesterMonsterMovesRight[20];
        MonsterThread[] threads = new MonsterThread[20];
        for (int i = 0; i < 20; i++) {
            threads[i] = new MonsterThread(board);
            tests[i] = new AsynchTesterMonsterMovesRight(threads[i]);
        }
        for (int i = 0; i < 20; i++) {
            tests[i].start();
        }
        for (int i = 0; i < 20; i++) {
            tests[i].test();
        }
        int n = 0;
        for (int i = 0; i < 20; i++) {
            System.out.println("-------------- " + i);
            Cell start = new Cell(0, i);
            MonsterThread t = threads[i];
            Monster m = t.getMonster();
            Cell c = m.getCurrentCell();
            n += t.getMonster().getCurrentCell().equals(start) ? 1 : 0;
        }
        assertThat(n > 15, is(false));
    }
}
