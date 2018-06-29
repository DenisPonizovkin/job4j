package ru.job4j.generic.container;

/**
 * Simple stack based on ContainerBasedOnLinkedList.
 *
 * @author Denis Ponizovkin.
 */
public class SimpleStack<E> {

    private ContainerBasedOnLinkedList<E> data = new ContainerBasedOnLinkedList<E>();

    /**
     * Poll last element from stack.
     * @return - last element.
     */
    public E poll() {
        E e = data.getLast();
        data.removeLast();
        return e;
    }

    /**
     * Add element in stack.
     * @param value - element.
     */
    public void push(E value) {
        data.add(value);
    }
}
