package ru.job4j.generic.container.list.linked;

import ru.job4j.generic.container.array.dynamic.DynamicArray;
import ru.job4j.utils.Pair;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Linked list based on array.
 */
public class LinkedListArrayBased<E> implements Iterable<E> {
    /**
     * First element.
     */
    private BiDirectionalNode<E> first = null;

    /**
     * Last element.
     */
    private BiDirectionalNode<E> last = null;


    /**
     * Add element.
     * @param value - adding value.
     */
    public void add(E value) {
        if (first == null) {
            BiDirectionalNode<E> node = new BiDirectionalNode<E>(value, null, null);
            first = node;
        } else if (first.next == null) {
            BiDirectionalNode<E> node = new BiDirectionalNode<E>(value, first, null);
            last = node;
            first.next = last;
        } else {
            BiDirectionalNode<E> node = new BiDirectionalNode<E>(value, last, null);
            last.next = node;
            last = node;
        }
    }

    /**
     * Get element by index.
     * @param index - index of element;
     * @return - data of element in index position.
     */
    public E get(int index) {
        int i = 0;
        for (BiDirectionalNode<E> start = first; start != null; start = start.next) {
           if (i++ == index) {
              return start.data;
           }
        }
        throw new NoSuchElementException();
    }

    /**
     * Remove element in index position.
     * @param index - position.
     */
    public void remove(int index) {
        int i = 0;
        BiDirectionalNode<E> prev = null;
        for (BiDirectionalNode<E> start = first; start != null; start = start.next) {
            if (i++ == index) {
                prev.next = start.next;
                return;
            }
            prev = start;
        }
        throw new NoSuchElementException();
    }

    @Override
    public Iterator<E> iterator() {
        return new FailFastIterator(first, last);
    }

    /**
     * Return data of last element.
     * @return - data of last element.
     */
    public E getLast() {
        return last.data;
    }

    /**
     * Remove last element.
     */
    public void removeLast() {
        BiDirectionalNode<E> start = first;
        while (start.next != last) {
            start = start.next;
        }
        last = start;
        last.next = null;
    }

    /**
     * Remove first element.
     */
    public void removeFirst() {
        first = first.next;
    }

    @Override
    public String toString() {
        String out = "LinkedListArrayBased{\n";
        Integer i = 0;
        for (Iterator it = iterator(); it.hasNext();) {
        //for (BiDirectionalNode<E> start = first; start != null; start = start.next) {
            out += "data[" + (i++) + "] = " + it.next() + "\n";
        }
        out += "}";
        return out;
    }

    public void print() {
        System.out.println(this.toString());
    }

    public void remove(E e) {
        BiDirectionalNode<E> cursor = first;
        do {
            if (cursor == null) {
                break;
            }
            if (e.equals(cursor.data)) {
                BiDirectionalNode<E> next = cursor.next;
                BiDirectionalNode<E> prev = cursor.prev;
                if (prev == null) {
                    first = next;
                    if (first != null) {
                        first.prev = null;
                    }
                    break;
                }
                if (next == null) {
                    last = prev;
                    last.next = null;
                    break;
                }
                prev.next = next;
                next.prev = prev;
                break;
            }
            cursor = cursor.next;
        } while ((cursor != null) && (cursor.next != last));
    }
}

