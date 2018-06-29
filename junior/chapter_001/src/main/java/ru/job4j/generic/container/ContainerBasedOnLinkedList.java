package ru.job4j.generic.container;

import ru.job4j.generic.container.list.linked.LinkedListArrayBased;

import java.util.Iterator;

/**
 * Container based on linked list.
 */
public class ContainerBasedOnLinkedList<E> implements Iterable<E> {

    private LinkedListArrayBased<E> list;

    public ContainerBasedOnLinkedList() {
        this.list = new LinkedListArrayBased<E>();
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

    /**
     * Get last element.
     * @return - last element.
     */
    public E getLast() {
        return list.getLast();
    }

    /**
     * Remove element in index position.
     * @param index - position.
     */
    public void remove(int index) {
        list.remove(index);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    public void removeLast() {
        list.removeLast();
    }

    public void addLast(E value) {
        list.add(value);
    }

    public void removeFirst() {
        list.removeFirst();
    }
}
