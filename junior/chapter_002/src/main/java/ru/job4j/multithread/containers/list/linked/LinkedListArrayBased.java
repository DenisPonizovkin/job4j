package ru.job4j.multithread.containers.list.linked;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Linked list based on array.
 */
@ThreadSafe
public class LinkedListArrayBased<E> {

    private Object lock = new Object();

    /**
     * First element.
     */
    @GuardedBy("lock")
    private BiDirectionalNode<E> first = null;

    /**
     * Last element.
     */
    @GuardedBy("lock")
    private BiDirectionalNode<E> last = null;


    /**
     * Add element.
     * @param value - adding value.
     */
    public void add(E value) {
        synchronized (lock) {
            if (first == null) {
                BiDirectionalNode<E> node = new BiDirectionalNode<E>(value, null, null);
                first = node;
            } else if (first.getNext() == null) {
                BiDirectionalNode<E> node = new BiDirectionalNode<E>(value, first, null);
                last = node;
                first.setNext(last);
            } else {
                BiDirectionalNode<E> node = new BiDirectionalNode<E>(value, last, null);
                last.setNext(node);
                last = node;
            }
        }
    }

    /**
     * Get element by index.
     * @param index - index of element;
     * @return - data of element in index position.
     */
    public E get(int index) {
        int i = 0;
        E e = null;
        synchronized (lock) {
            for (BiDirectionalNode<E> start = first; start != null; start = start.getNext()) {
                if (i++ == index) {
                    e = start.getData();
                }
            }
        }
        if (e == null) {
            throw new NoSuchElementException();
        }
        return e;
    }

    public void update(int index, E e) {
        int i = 0;
        boolean ok = false;
        synchronized (lock) {
            for (BiDirectionalNode<E> start = first; start != null; start = start.getNext()) {
                if (i++ == index) {
                    ok = true;
                    start.setData(e);
                }
            }
        }
        if (!ok) {
            throw new NoSuchElementException();
        }
    }

   /**
     * Return data of last element.
     * @return - data of last element.
     */
    public E getLast() {
        E e = null;
        synchronized (lock) {
            e = last.getData();
        }
        return e;
    }

    /**
     * Remove last element.
     */
    public void removeLast() {
        synchronized (lock) {
            BiDirectionalNode<E> start = first;
            while (start.getNext() != last) {
                start = start.getNext();
            }
            last = start;
            last.setNext(null);
        }
    }

    /**
     * Remove first element.
     */
    public void removeFirst() {
        synchronized (lock) {
            first = first.getNext();
        }
    }

    public void remove(E e) {
        boolean ok = false;
        synchronized (lock) {
            BiDirectionalNode<E> cursor = first;
            do {
                if (cursor == null) {
                    break;
                }
                if (e.equals(cursor.getData())) {
                    BiDirectionalNode<E> next = cursor.getNext();
                    BiDirectionalNode<E> prev = cursor.getPrev();
                    if (prev == null) {
                        first = next;
                        if (first != null) {
                            first.setPrev(null);
                        }
                        ok = true;
                        break;
                    }
                    if (next == null) {
                        last = prev;
                        last.setNext(null);
                        ok = true;
                        break;
                    }
                    prev.setNext(next);
                    next.setPrev(prev);
                    ok = true;
                    break;
                }
                cursor = cursor.getNext();
            } while ((cursor != null) && (cursor != last));
        }
        if (!ok) {
            throw new NoSuchElementException();
        }
    }
}

