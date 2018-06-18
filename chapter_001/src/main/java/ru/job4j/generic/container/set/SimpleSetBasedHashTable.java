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

    public SimpleSetBasedHashTable() {
        hashTable = new DynamicArray<E>(max * 2);
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
        }
        return ok;
    }

    /**
     * Check if the set contains the element.
     * @param e - the element.
     * @return - true if the set contains the element.
     */
    boolean contains(E e) {
        int id = getId(e);
        boolean is = false;
        if (id < hashTable.size()) {
            hashTable.get(id);
            is = true;
        }
        return is;
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
}
