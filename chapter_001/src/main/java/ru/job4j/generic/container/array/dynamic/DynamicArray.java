package ru.job4j.generic.container.array.dynamic;

import ru.job4j.generic.container.list.linked.LinkedListArrayBased;
import ru.job4j.iterator.failfast.FailFastArrayIterator;
import ru.job4j.utils.Pair;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Dynamic container.
 *
 * @author Ponizovkin Denis.
 */
public class DynamicArray<E> implements Iterable<E> {
    /**
     * Storing data.
     */
    private Object[] data;

    /**
     * Number of elements in data.
     */
    private int size = 0;

    /**
     * Number of modifications.
     */
    private int modCount = 0;

    /**
     * Constructor.
     */
    public DynamicArray() {
        data = new Object[10];
    }

    /**
     * Constructor.
     */
    public DynamicArray(int n) {
        data = new Object[n];
    }

    /**
     * Add an element to the data.
     * @param e - adding element.
     */
    public void add(E e) {
        modCount++;
        if (size < data.length) {
            data[size++] = e;
            return;
        }
        resize(data.length * 2);
    }

    /**
     * Get element by id.
     * @param index - id of getting element.
     * @return - element in index position.
     */
    public E get(int index) {
        if (index < size) {
            return (E) data[index];
        }
        throw new NoSuchElementException("No elements in " + index + " position");
    }

    /**
     * Is element in array.
     * @param e
     * @return
     */
    public boolean contains(E e) {
        boolean is = false;
        for (Iterator it = iterator(); it.hasNext();) {
            if (it.next().equals(e)) {
                is = true;
                break;
            }
        }
        return is;
    }

    /**
     * Size of data.
     * @return - size.
     */
    public int size() {
        return size;
    }

    /**
     * Number of modifications.
     * @return - number of modifications.
     */
    public int modCount() {
        return modCount;
    }

    @Override
    public Iterator<E> iterator() {
        return new FailFastArrayIterator<E>(this, modCount);
    }

    public void remove(int id) {
        --size;
        for (int i = id; i < size; i++) {
            data[i] = data[i + 1];
        }
    }

    public void clear() {
        size = 0;
    }

    public int containerSize() {
        return data.length;
    }

    public boolean isIdElement(int id) {
        return data[id] != null;
    }

    public void resize(int length) {
        Object[] tmp = new Object[length];
        System.arraycopy(data, 0, tmp, 0, data.length);
        data = new Object[length];
        System.arraycopy(tmp, 0, data, 0, tmp.length);
    }

    public void set(int i, E v) {
        if (i >= data.length) {
           throw new IndexOutOfBoundsException();
        }
        if (data[i] == null) {
            size++;
        }
        data[i] = v;
    }
}
