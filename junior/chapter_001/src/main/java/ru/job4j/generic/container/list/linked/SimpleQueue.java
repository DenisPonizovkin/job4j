package ru.job4j.generic.container.list.linked;

import ru.job4j.generic.container.ContainerBasedOnLinkedList;
import ru.job4j.generic.container.SimpleStack;

/**
 * Simple queue based on ContainerBasedOnLinkedList.
 *
 * @author Denis Ponizovkin.
 */
public class SimpleQueue<E> extends SimpleStack<E> {

    private ContainerBasedOnLinkedList<E> data;

    /**
     * Constructor.
     */
    public SimpleQueue() {
        this.data = new ContainerBasedOnLinkedList<E>();
    }

    @Override
    public E poll() {
        E e = data.get(0);
        data.removeFirst();
        return e;
    }
}
