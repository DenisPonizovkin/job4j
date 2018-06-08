package ru.job4j.iterator.failfast;

import ru.job4j.generic.array.dynamic.DynamicArray;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Fail-fast iterator for dynamic array.
 */
public class FailFastArrayIterator<E> implements Iterator {

    private DynamicArray<E> dar;
    private int expectedModCount = 0;
    private int cursor = 0;

    public FailFastArrayIterator(DynamicArray<E> data, int modCount) {
        dar = data;
        expectedModCount = modCount;
    }

    @Override
    public boolean hasNext() {
        return cursor < dar.size();
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (expectedModCount != dar.modCount()) {
            throw new ConcurrentModificationException();
        }
        return dar.get(cursor++);
    }
}
