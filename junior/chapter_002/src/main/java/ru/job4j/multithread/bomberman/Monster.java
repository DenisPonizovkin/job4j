package ru.job4j.multithread.bomberman;

import java.util.Random;

public class Monster extends Entity {

    public Monster(Board board, Integer x, Integer y) {
        super(board, x, y);
    }

    public Monster(Board board, Cell position) {
        super(board, position);
    }

    @Override
    protected void busyCellAction() throws InterruptedException {
        randomMoving();
   }

    public void randomMoving() throws InterruptedException {
        Random rnd = new Random();
        int dir = rnd.nextInt(4);
        switch (dir) {
            case 0:
                left();
                break;
            case 1:
                right();
                break;
            case 2:
                up();
                break;
            default: down();
        }
    }
}
