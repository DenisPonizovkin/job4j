package ru.job4j.multithread.bomberman;

public abstract class Entity {

    private final Cell position;
    private final int height;
    private final int width;
    private final Board board;

    public Entity(Board board, Integer x, Integer y) {
        this.board = board;
        height = board.getHeightLimit();
        width = board.getWidthLimit();
        this.position = new Cell(x, y);
    }

    public Entity(Board board, Cell position) {
        this.board = board;
        height = board.getHeightLimit();
        width = board.getWidthLimit();
        this.position = position;
    }

    public void init() throws InterruptedException {
        if (position != null) {
            board.standOn(position);
        }
    }

    public void up() throws InterruptedException {
        if (position.getY() < height) {
            Cell from = new Cell(position.getX(), position.getY());
            Cell to = new Cell(position.getX(), position.getY() + 1);
            try {
                board.move(from, to);
                changePosition(to);
            } catch (Board.CellIsBusy e) {
                busyCellAction();
            }
        }
    }

    public void down() throws InterruptedException {
        if (position.getY() > 0) {
            Cell from = new Cell(position.getX(), position.getY());
            Cell to = new Cell(position.getX(), position.getY() - 1);
            try {
                board.move(from, to);
                changePosition(to);
            } catch (Board.CellIsBusy e) {
                busyCellAction();
            }
        }
    }

    public void right() throws InterruptedException {
        if (position.getX() < width) {
            Cell from = new Cell(position.getX(), position.getY());
            Cell to = new Cell(position.getX() + 1, position.getY());
            try {
                board.move(from, to);
                changePosition(to);
            } catch (Board.CellIsBusy e) {
                busyCellAction();
            }
        }
    }

    public void left() throws InterruptedException {
        if (position.getX() > 0) {
            Cell from = new Cell(position.getX(), position.getY());
            Cell to = new Cell(position.getX() - 1, position.getY());
            try {
                board.move(from, to);
                changePosition(to);
            } catch (Board.CellIsBusy e) {
                busyCellAction();
            }
        }
    }

    public Cell getCurrentCell() {
        return position;
    }

    protected abstract void busyCellAction() throws InterruptedException;

    protected int boardWidthLimit() {
        return board.getWidthLimit();
    }

    protected int boardHeightLimit() {
        return board.getHeightLimit();
    }

    private void changePosition(Cell to) {
        position.setX(to.getX());
        position.setY(to.getY());
    }
}
