package ru.job4j.iterator;

import ru.job4j.list.SimpleArrayList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayListIterator<E> implements Iterator<E> {
    private SimpleArrayList<E> data;
    private int cursor = 0;

    public SimpleArrayListIterator(SimpleArrayList<E> data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return cursor < data.getSize();
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data.get(cursor++);
    }
}
