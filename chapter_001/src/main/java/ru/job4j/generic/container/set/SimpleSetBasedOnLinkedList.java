package ru.job4j.generic.container.set;

import ru.job4j.generic.container.list.linked.LinkedListArrayBased;

import java.util.Iterator;

/**
 * Simple set based on linked list.
 * @param <E>.
 * @author Denis Ponizovkin.
 */
public class SimpleSetBasedOnLinkedList<E> implements Iterable<E> {

    private LinkedListArrayBased<E> data;

    /**
     * Constructor.
     */
    public SimpleSetBasedOnLinkedList() {
        this.data = new LinkedListArrayBased<E>();
    }

    /**
     * Add an element to the set.
     * @param e
     */
    void add(E e) {
        boolean is = false;
        for (Iterator it = data.iterator(); it.hasNext();) {
            if (it.next().equals(e)) {
               is = true;
            }
        }
        if (!is) {
            data.add(e);
        }
     }

    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }
}
