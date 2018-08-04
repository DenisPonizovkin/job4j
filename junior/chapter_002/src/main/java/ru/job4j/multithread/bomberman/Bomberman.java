package ru.job4j.multithread.bomberman;

public class Bomberman extends Entity {

    public Bomberman(Board board, Integer x, Integer y) {
        super(board, x, y);
    }

    public Bomberman(Board board, Cell position) {
        super(board, position);
    }

    @Override
    protected void busyCellAction() {
        return;
    }
}
