package ru.job4j.iterator.even;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator which returns even number.
 */
public class IteratorArrayEvenNumbers implements Iterator {
    /**
     * Data.
     */
    private int[] array;
    /**
     * Current index.
     */
    private int index = 0;
    /**
     * If there is no even number in array then isEvenNumber() method return this value.
     */
    private final int noEvenNumberId = -1;

    public IteratorArrayEvenNumbers(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return evenNumberId() != -1;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        for (; index < array.length; index++) {
            if (isEvenNumber(array[index])) {
                int elem = array[index];
                ++index;
                return elem;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     *
     * @return id of first even number after index.
     */
    private int evenNumberId() {
        for (int i = index; i < array.length; i++) {
            if (isEvenNumber(array[i])) {
                return i;
            }
        }
        return noEvenNumberId;
    }

    /**
     *
     * @param n - integer number.
     * @return - return true if the n is even, false otherwise.
     */
    private boolean isEvenNumber(int n) {
        return n % 2 == 0;
    }
}
