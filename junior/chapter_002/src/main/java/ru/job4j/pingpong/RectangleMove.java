package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class RectangleMove implements Runnable {
    private final Rectangle rect;
    private final int limitX;
    private final int limitY;
    private final int up = 1 << 0;
    private final int down = 1 << 1;
    private final int left = 1 << 2;
    private final int right = 1 << 3;
    private int dir;

    public RectangleMove(Rectangle rect, int limitX, int limitY) {
        this.rect = rect;
        this.limitX = limitX;
        this.limitY = limitY;
        dir = right;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            move();
            try {
                if (this.rect.getX() == 0) {
                    //System.out.println("===> Right");
                    Random rnd = new Random(System.currentTimeMillis());
                    changeDir(right, rnd.nextInt(2) > 0 ? up : down);
                }
                if (this.rect.getX() == limitX) {
                    //System.out.println("===> left");
                    Random rnd = new Random(System.currentTimeMillis());
                    changeDir(left, rnd.nextInt(2) > 0 ? up : down);
                }
                if (this.rect.getY() == 0) {
                    //System.out.println("===> up");
                    Random rnd = new Random(System.currentTimeMillis());
                    changeDir(rnd.nextInt(2) > 0 ? right : left, up);
                }
                if (this.rect.getY() == limitY) {
                    //System.out.println("===> down");
                    Random rnd = new Random(System.currentTimeMillis());
                    changeDir(rnd.nextInt(2) > 0 ? right : left, down);
                }
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeDir(int xDir, int yDir) {
        dir = (xDir | yDir);
    }

    private void move() {
        if ((dir & up) > 0) {
            this.rect.setY(this.rect.getY() + 1);
        }
        if ((dir & down) > 0) {
            this.rect.setY(this.rect.getY() - 1);
        }
        if ((dir & left) > 0) {
            this.rect.setX(this.rect.getX() - 1);
        }
        if ((dir & right) > 0) {
            this.rect.setX(this.rect.getX() + 1);
        }
    }
}
