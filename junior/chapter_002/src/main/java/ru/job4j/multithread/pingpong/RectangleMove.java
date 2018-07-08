package ru.job4j.multithread.pingpong;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class RectangleMove implements Runnable {
    private final Rectangle rect;
    private final int limitX;
    private final int limitY;

    public RectangleMove(Rectangle rect, int limitX, int limitY) {
        this.rect = rect;
        this.limitX = limitX;
        this.limitY = limitY;
    }

    @Override
    public void run() {
        int x = 1;
        int y = 1;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (this.rect.getX() == limitX) {
                    x = -1;
                }
                if (this.rect.getX() == 0) {
                    x = 1;
                }
                if (this.rect.getY() == limitY) {
                    y = -1;
                }
                if (this.rect.getY() == 0) {
                    y = 1;
                }
                this.rect.setX(this.rect.getX() + x);
                this.rect.setY(this.rect.getY() + y);
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
