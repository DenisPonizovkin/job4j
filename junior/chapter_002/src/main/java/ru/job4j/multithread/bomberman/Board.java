package ru.job4j.multithread.bomberman;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Board {

    class CellIsBusy extends RuntimeException {

    }

    public void standOn(Cell c) throws InterruptedException {
        final ReentrantLock lock = board[c.getY()][c.getX()];
        try {
            if (!lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                throw new CellIsBusy();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private final int m;
    private final int n;
    private final ReentrantLock[][] board;

    public Board(int m, int n) {
        this.m = m;
        this.n = n;
        board = new ReentrantLock[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] = new ReentrantLock();
            }
        }
    }

    public int getHeightLimit() {
        return m;
    }

    public int getWidthLimit() {
        return n;
    }

    public ReentrantLock getCell(int x, int y) {
        return board[x][y];
    }

    public void move(Cell from, Cell to) throws InterruptedException {
        if (to.getX() >= getWidthLimit() || to.getY() >= getHeightLimit()) {
           throw new CellIsBusy();
        }
        final ReentrantLock lock = board[to.getY()][to.getX()];
        boolean ok = false;
        boolean canLock = false;
        try {
            if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                canLock = true;
                board[from.getY()][from.getX()].unlock();
                ok = true;
            }
        } catch (InterruptedException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (!ok) {
                if (canLock) {
                    board[to.getY()][to.getX()].unlock();
                }
                throw new CellIsBusy();
            }
        }
    }

    public boolean isLocked(Cell cell) {
        final ReentrantLock lock = board[cell.getY()][cell.getX()];
        return lock.isLocked();
    }

    public void addBlock(Cell c) throws InterruptedException {
        final ReentrantLock lock = board[c.getY()][c.getX()];
        try {
            if (!lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                board[c.getY()][c.getX()].unlock();
            }
        } catch (InterruptedException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
