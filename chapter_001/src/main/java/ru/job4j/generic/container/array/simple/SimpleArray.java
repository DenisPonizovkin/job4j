package ru.job4j.generic.array.simple;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic wrapper for array.
 */
public class SimpleArray<T> implements Iterable<T> {

    /**
     * Storing data.
     */
    private Object[] data;
    /**
     * Current index position.
     */
    private int cursor = 0;

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
        if (cursor == data.length) {
            throw new ArrayStoreException();
        }
        data[cursor++] = e;
    }

    /**
     * Number of elements in array.
     * @return - number of elements.
     */
    public int getSize() {
        return cursor;
    }

    /**
     * Reserved size.
     * @return - reserved size.
     */
    public int capacity() {
        return data.length;
    }

    /**
     * Set element in [index] place.
     * @param index - index of element ini array.
     * @param e - element.
     */
    public void set(int index, T e) {
        if (index > cursor - 1) {
            throw new IndexOutOfBoundsException();
        }
        data[index] = e;
    }

    /**
     * Delete element at index place.
     */
    public void delete(int index) {
        if (index > cursor - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (index < cursor - 1) {
            for (int i = index + 1; i < cursor; i++) {
                data[i - 1] = data[i];
            }
        }
        cursor--;
    }

    /**
     * Get element from array at index position.
     * @param index - index of element.
     * @return element.
     */
    public T get(int index) {
        if (index > cursor) {
            throw new IndexOutOfBoundsException();
        }
        return (T) data[index];
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleArrayIterator<T>(this);
    }

    /**
     * Simple array iterator.
     * @param <T>
     */
    class SimpleArrayIterator<T> implements Iterator<T> {

        SimpleArray<T> array;
        int cursor = 0;

        SimpleArrayIterator(SimpleArray<T> array) {
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return cursor < array.getSize();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array.get(cursor++);
        }
    }

}

