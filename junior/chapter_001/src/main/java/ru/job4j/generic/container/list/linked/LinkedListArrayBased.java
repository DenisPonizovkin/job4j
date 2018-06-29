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

    /**
     * Get element by index.
     * @param index - index of element;
     * @return - data of element in index position.
     */
    public E get(int index) {
        int i = 0;
        for (BiDirectionalNode<E> start = first; start != null; start = start.getNext()) {
           if (i++ == index) {
              return start.getData();
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
        for (BiDirectionalNode<E> start = first; start != null; start = start.getNext()) {
            if (i++ == index) {
                prev.setNext(start.getNext());
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
        return last.getData();
    }

    /**
     * Remove last element.
     */
    public void removeLast() {
        BiDirectionalNode<E> start = first;
        while (start.getNext() != last) {
            start = start.getNext();
        }
        last = start;
        last.setNext(null);
    }

    /**
     * Remove first element.
     */
    public void removeFirst() {
        first = first.getNext();
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
            if (e.equals(cursor.getData())) {
                BiDirectionalNode<E> next = cursor.getNext();
                BiDirectionalNode<E> prev = cursor.getPrev();
                if (prev == null) {
                    first = next;
                    if (first != null) {
                        first.setPrev(null);
                    }
                    break;
                }
                if (next == null) {
                    last = prev;
                    last.setNext(null);
                    break;
                }
                prev.setNext(next);
                next.setPrev(prev);
                break;
            }
            cursor = cursor.getNext();
        } while ((cursor != null) && (cursor.getNext() != last));
    }
}

