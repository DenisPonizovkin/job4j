package ru.job4j.generic.array.simple;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic wrapper for array.
 */
public class SimpleArray<T> implements Iterable<T> {

    private Object[] data;
    int currentPosition = 0;

    /**
     * Simple array iterator.
     * @param <T>
     */
    class SimpleArrayIterator<T> implements Iterator<T> {

        SimpleArray<T> array;
        int currentPosition = 0;

        SimpleArrayIterator(SimpleArray<T> array) {
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return currentPosition < array.getSize();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array.get(currentPosition++);
        }
    }

    /**
     * Number of elements in array.
     * @return - number of elements.
     */
    public int getSize() {
        return currentPosition;
    }

    /**
     * Reserved size.
     * @return - reserved size.
     */
    public int capacity() {
        return data.length;
    }

    /**
     * Constructor.
     * @param n - number of elements in array (data member);
     */
    public SimpleArray(int n) {
        data = new Object[n];
    }

    /**
     * Add element in array.
     * @param e - new element.
     */
    public void add(T e) {
        if (currentPosition == data.length) {
            throw new ArrayStoreException();
        }
        data[currentPosition++] = e;
    }

    /**
     * Set element in [index] place.
     * @param index - index of element ini array.
     * @param e - element.
     */
    public void set(int index, T e) {
        if (index > currentPosition - 1) {
            throw new IndexOutOfBoundsException();
        }
        data[index] = e;
    }

    /**
     * Delete element at index place.
     * @param index - index of element.
     */
    public void delete(int index) {
        if (index > currentPosition - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (index < currentPosition - 1) {
            for (int i = index + 1; i < currentPosition; i++) {
                data[i - 1] = data[i];
            }
        }
        currentPosition--;
    }

    /**
     * Get element from array at index position.
     * @param index - index of element.
     * @return element.
     */
    T get(int index) {
        if (index > currentPosition) {
            throw new IndexOutOfBoundsException();
        }
        return (T) data[index];
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleArrayIterator<T>(this);
    }

    public void print() {
        for (Iterator<T> it = iterator(); it.hasNext();) {
            T e = it.next();
            System.out.println("" + e);
        }
    }
}

