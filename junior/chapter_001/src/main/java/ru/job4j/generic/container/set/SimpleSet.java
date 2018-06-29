package ru.job4j.generic.container.set;

import ru.job4j.iterator.SimpleArrayListIterator;
import ru.job4j.list.SimpleArrayList;

import java.util.Iterator;

/**
 * Simple collection based on SimpleList.
 * @param <E>
 * @authr Denis Ponizovkin
 */
public class SimpleSet<E> implements Iterable<E> {

    private SimpleArrayList<E> data;

    /**
     * Contstructor.
     */
    public SimpleSet() {
        this.data = new SimpleArrayList<E>();
    }

    /**
     * Add an element to the set.
     * @param e
     */
    public void add(E e) {
        boolean is = false;
        for (Integer i = 0; i < data.getSize(); i++) {
           if (data.get(i).equals(e)) {
                is = true;
                break;
           }
        }
        if (!is) {
            data.add(e);
        }
    }

    /**
     * Size of the data.
     * @return size of the data.
     */
    public int size() {
        return data.getSize();
    }

    @Override
    public Iterator<E> iterator() {
        return new SimpleArrayListIterator(data);
    }
}
