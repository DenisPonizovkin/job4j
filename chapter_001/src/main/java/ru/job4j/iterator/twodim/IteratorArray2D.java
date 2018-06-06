package ru.job4j.iterator.twodim;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator for two dimensional array.
 */
public class IteratorArray2D implements Iterator {

    /**
     * Data.
     */
    private int[][] array;
    /**
     * Current column index.
     */
    private int indexColumn = 0;
    /**
     * Current row index.
     */
    private int indexRow = 0;
    /**
     * Number of all elements of array.
     */
    private int numberOfElements;
    /**
     * Current number of elements. It used in hasNext() method.
     */
    private int currentNumber = 0;

    public IteratorArray2D(final int[][] array) {
        this.array = array;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                numberOfElements++;
            }
        }
    }

    @Override
    public boolean hasNext() {
        boolean is = currentNumber < numberOfElements;
        System.out.println(currentNumber + " <=> " + numberOfElements + ": " + is);
        return is;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        currentNumber++;
        int element = array[indexColumn][indexRow];
        if (indexRow == array[indexColumn].length - 1) {
            indexColumn++;
            if (hasNext()) {
                indexRow = 0;
            }
        } else {
            indexRow++;
        }
        return element;
    }
}
