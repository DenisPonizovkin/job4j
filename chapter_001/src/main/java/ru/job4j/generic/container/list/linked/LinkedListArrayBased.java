package ru.job4j.generic.container.list.linked;

import ru.job4j.generic.container.array.dynamic.DynamicArray;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Linked list based on array.
 */
public class LinkedListArrayBased<E> implements Iterable<E> {

    /**
     * Storing data;
     */
    private DynamicArray<E> data;
    /**
     * First element.
     */
    Node<E> first = null;
    /**
     * Last element.
     */
    Node<E> last = null;

    /**
     * Add element.
     * @param value - adding value.
     */
    public void add(E value) {
        if (first == null) {
            Node<E> node = new Node<E>(value, null, null);
            first = node;
            return;
        }
        if (first.next == null) {
            Node<E> node = new Node<E>(value, first, null);
            last = node;
            first.next = last;
            return;
        }
        Node<E> node = new Node<E>(value, last, null);
        last.next = node;
        last = node;
    }

    /**
     * Print data.
     */
    public void print() {
        System.out.println("LL: ");
        for (Node<E> start = first; start != null;) {
           System.out.print(start.data + " ");
           start = start.next;
        }
        System.out.print("\n");
    }

    /**
     * Get element by index.
     * @param index - index of element;
     * @return - data of element in index position.
     */
    public E get(int index) {
        int i = 0;
        for (Node<E> start = first; start != null;) {
           if (i == index) {
              return start.data;
           }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }

    /**
     * Node of linked list.
     * @param <E> - type of node data.
     */
    class Node<E> {
        E data;
        public Node<E> prev;
        public Node<E> next;

        Node(E data, Node<E> prev, Node<E> next) {
           this.data = data;
           this.prev = prev;
           this.next = next;
        }
    }
}
