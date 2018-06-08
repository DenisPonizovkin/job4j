package ru.job4j.generic.container;

import ru.job4j.generic.container.list.linked.LinkedListArrayBased;

import java.util.Iterator;

/**
 * Container based on linked list.
 */
public class ContainerBasedOnLinkedList<E> implements Iterable<E> {

    private LinkedListArrayBased<E> list;

    public ContainerBasedOnLinkedList(LinkedListArrayBased<E> list) {
        this.list = list;
    }

    /**
     * Add value.
     * @param value - adding value.
     */
    public void add(E value) {
        list.add(value);
    }

    /**
     * Get element in index position.
     * @param index - index.
     * @return - element in index position.
     */
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
}
