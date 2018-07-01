package ru.job4j.multithread.containers.list.linked;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.NoSuchElementException;

/**
 * threadsafe dynamic container.
 *
 * @author Ponizovkin Denis.
 */
@ThreadSafe
public class DynamicArray<E> {

    private Object lock = new Object();

    /**
     * Storing data.
     */
    @GuardedBy("lock")
    private Object[] data;

    /**
     * Number of elements in data.
     */
    @GuardedBy("lock")
    private int size = 0;

    /**
     * Number of modifications.
     */
    @GuardedBy("lock")
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
        synchronized (lock) {
            modCount++;
            if (size < data.length) {
                data[size++] = e;
            } else {
                resize(data.length * 2);
            }
        }
    }

    /**
     * Get element by id.
     * @param index - id of getting element.
     * @return - element in index position.
     */
    public E get(int index) {
        E e = null;
        synchronized (lock) {
            if (index >= size) {
                throw new NoSuchElementException("No elements in " + index + " position");
            }
            e = (E) data[index];
        }
        return e;
    }

    /**
     * Is element in array.
     * @param e
     * @return
     */
    public boolean contains(E e) {
        boolean is = false;
        synchronized (lock) {
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals(e)) {
                    is = true;
                    break;
                }
            }
        }
        return is;
    }

    /**
     * Size of data.
     * @return - size.
     */
    public int size() {
        int n = 0;
        synchronized (lock) {
            n = size;
        }
        return n;
    }

    /**
     * Number of modifications.
     * @return - number of modifications.
     */
    public int modCount() {
        int cnt = 0;
        synchronized (lock) {
            cnt = modCount;
        }
        return cnt;
    }

    public void remove(int id) {
        synchronized (lock) {
            if (!isIdElement(id)) {
                throw new NoSuchElementException();
            }
            --size;
            for (int i = id; i < size; i++) {
                data[i] = data[i + 1];
            }
        }
    }

    public void clear() {
        synchronized (lock) {
            size = 0;
        }
    }

    public int containerSize() {
        synchronized (lock) {
            return data.length;
        }
    }

    public boolean isIdElement(int id) {
        boolean is = false;
        synchronized (lock) {
            if ((size > 0) && (data[id] != null)) {
                is = true;
            }
        }
        return is;
    }

    public void resize(int length) {
        Object[] tmp = new Object[length];
        System.arraycopy(data, 0, tmp, 0, data.length);
        data = new Object[length];
        System.arraycopy(tmp, 0, data, 0, tmp.length);
    }

    public void set(int i, E v) {
        synchronized (lock) {
            if (i >= data.length) {
                throw new IndexOutOfBoundsException();
            }
            if (data[i] == null) {
                size++;
            }
            data[i] = v;
        }
    }
}
