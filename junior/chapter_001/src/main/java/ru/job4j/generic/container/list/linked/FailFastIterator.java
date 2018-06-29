package ru.job4j.generic.container.list.linked;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FailFastIterator<E> implements Iterator<E> {

    private BiDirectionalNode<E> first;
    private BiDirectionalNode<E> last;

    public FailFastIterator(BiDirectionalNode<E> first, BiDirectionalNode<E> last) {
        this.first = first;
        this.last = last;
    }

    @Override
    public boolean hasNext() {
        return first != null;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        E data = first.getData();
        first = first.getNext();
        return data;
    }
}
