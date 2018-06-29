package ru.job4j.generic.container.list.linked;

public class BiDirectionalNode<E> {
    private E data;
    private BiDirectionalNode<E> prev;
    private BiDirectionalNode<E> next;

    BiDirectionalNode(E data, BiDirectionalNode<E> prev, BiDirectionalNode<E> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public BiDirectionalNode<E> getPrev() {
        return prev;
    }

    public void setPrev(BiDirectionalNode<E> prev) {
        this.prev = prev;
    }

    public BiDirectionalNode<E> getNext() {
        return next;
    }

    public void setNext(BiDirectionalNode<E> next) {
        this.next = next;
    }
}
