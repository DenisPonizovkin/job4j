package ru.job4j.generic.container.set;

import ru.job4j.generic.container.array.dynamic.DynamicArray;

import java.util.Iterator;

/**
 * Simple set based on hash table.
 * @param <E> - generic.
 * @author Denis Ponizovkin.
 */
public class SimpleSetBasedHashTable<E> implements Iterable<E> {

    private DynamicArray<E> hashTable;
    private final int max = 100;

    /**
     * Constructor.
     */
    public SimpleSetBasedHashTable() {
        hashTable = new DynamicArray<E>(max * 2);
    }

    /**
     * Get size of set.
     * @return size.
     */
    public int size() {
        return hashTable.size();
    }

    /**
     * Add the element to the set.
     * @param e - the element.
     * @return - true if the set doesn't contain the element and adding success.
     */
    boolean add(E e) {
        boolean ok = false;
        if (!contains(e)) {
            hashTable.add(e);
            ok = true;
        }
        return ok;
    }

    /**
     * Check if the set contains the element.
     * @param e - the element.
     * @return - true if the set contains the element.
     */
    boolean contains(E e) {
        return hashTable.contains(e);
    }

    /**
     * Remove the element.
     * @param e - the element.
     * @return - true if the set contains the element and removing is success.
     */
    boolean remove(E e) {
        boolean ok = false;
        if (contains(e)) {
           hashTable.remove(getId(e));
           ok = true;
        }
        return ok;
    }

    /**
     * Get id of the element in hashTable.
     * @param e
     * @return - id.
     */
    private int getId(E e) {
       return e.hashCode() % max;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    public void clear() {
        hashTable.clear();
    }
}
