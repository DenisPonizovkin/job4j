package ru.job4j.generic.container.list.linked;

public class BiDirectionalNode<E> {
    E data;
    public BiDirectionalNode<E> prev;
    public BiDirectionalNode<E> next;

    BiDirectionalNode(E data, BiDirectionalNode<E> prev, BiDirectionalNode<E> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}
