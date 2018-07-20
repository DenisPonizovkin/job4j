package ru.job4j.multithread.bomberman;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Board {

    private final int m;
    private final int n;
    private final ReentrantLock[][] board;
    private final Cell position;

    public Board(int m, int n) {
        this.m = m;
        this.n = n;
        board = new ReentrantLock[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] = new ReentrantLock();
            }
        }
        position = new Cell(0, 0);
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
        final ReentrantLock lock = board[to.getY()][to.getX()];
        boolean ok = false;
        try {
            if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                board[from.getY()][from.getX()].unlock();
                position.setX(to.getX());
                position.setY(to.getY());
            }
        } catch (InterruptedException e) {
            throw e;
        } finally {
            if (!ok) {
                board[to.getY()][to.getX()].unlock();
                position.setX(from.getX());
                position.setY(from.getY());
            }
        }
    }

    public boolean isLocked(Cell cell) {
        final ReentrantLock lock = board[cell.getY()][cell.getX()];
        return lock.isLocked();
    }

}
