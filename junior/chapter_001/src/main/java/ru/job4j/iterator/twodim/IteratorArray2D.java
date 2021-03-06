package ru.job4j.iterator.twodim;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator for two dimensional container.
 */
public class IteratorArray2D implements Iterator {

    /**
     * Data.
     */
    private int[][] array;
    /**
     * Current row index.
     */
    private int indexRow = 0;
    /**
     * Current column index.
     */
    private int indexColumn = 0;

    public IteratorArray2D(final int[][] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return indexRow < array.length;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int element = array[indexRow][indexColumn];
        if (indexColumn == array[indexRow].length - 1) {
            indexRow++;
            indexColumn = 0;
        } else {
            indexColumn++;
        }
        return element;
    }
}
