package ru.job4j.generic.container;

import java.util.NoSuchElementException;

import ru.job4j.generic.container.array.dynamic.DynamicArray;
import ru.job4j.generic.container.list.linked.LinkedListArrayBased;
import ru.job4j.utils.Pair;

import java.util.Iterator;

public class HashTable<K, V> implements Iterable<K> {

    private DynamicArray<LinkedListArrayBased<Pair<K, V>>> data;

    /**
     * Constructor.
     */
    public HashTable() {
        data = new DynamicArray<LinkedListArrayBased<Pair<K, V>>>(16);
        for (int i = 0; i < data.containerSize(); i++) {
            data.add(new LinkedListArrayBased<Pair<K, V>>());
        }
    }

    /**
     * Contructor;
     * @param n - size of collection.
     */
    public HashTable(int n) {
        data = new DynamicArray<LinkedListArrayBased<Pair<K, V>>>(n * 2);
        for (int i = 0; i < data.containerSize(); i++) {
            data.add(new LinkedListArrayBased<Pair<K, V>>());
        }
    }

    /**
     * Insert value by key.
     * @param key
     * @param value
     * @return
     */
    public boolean insert(K key, V value) {

        resizeIfNeed(key);
        int id = index(key);
        boolean insert = true;
        LinkedListArrayBased<Pair<K, V>> bucket = data.get(id);
        for (Iterator it = bucket.iterator(); it.hasNext();) {
            Pair<K, V> pair = (Pair<K, V>) it.next();
            if (key.equals(pair.key)) {
                pair.value = value;
                insert = false;
                break;
            }
        }
        if (insert) {
            insert(id, new Pair<K, V>(key, value));
        }
        return insert;
    }

    /**
     * Resize data if hashCode value is greater then container size.
     * @param key
     */
    private void resizeIfNeed(K key) {
        int hash = key.hashCode();
        System.out.println("resize if need: " + hash + "/" + data.containerSize());
        if (hash >= data.containerSize()) {
            data.resize(hash * 2);
        }
        for (int i = 0; i < data.containerSize(); i++) {
           if (data.isIdElement(i)) {
              continue;
           }
           data.set(i, new LinkedListArrayBased<Pair<K, V>>());
        }
    }

    /**
     * Get value by key.
     * @param key
     * @return
     */
    public V get(K key) {

        int id = index(key);
        LinkedListArrayBased<Pair<K, V>> bucket = data.get(id);
        V v = null;
        for (Iterator it = bucket.iterator(); it.hasNext();) {
            Pair<K, V> pair = (Pair<K, V>) it.next();
            if (key.equals(pair.key)) {
                v = pair.value;
                break;
            }
        }
		if (v == null) {
			throw new NoSuchElementException();
		}
        return v;
    }

    /**
     * Delete element from collection by key.
     * @param key
     * @return true if deleting is success.
     */
    public boolean delete(K key) {
        int id = index(key);
        LinkedListArrayBased<Pair<K, V>> bucket = data.get(id);
        boolean ok = false;
        for (Iterator it = bucket.iterator(); it.hasNext();) {
            Pair<K, V> pair = (Pair<K, V>) it.next();
            if (key.equals(pair.key)) {
                ok = true;
                bucket.remove(pair);
                break;
            }
        }
        return ok;
    }

    /**
     * Insert new pair.
     */
    private void insert(int id, Pair<K, V> pair) {
        data.get(id).add(pair);
    }

    /**
     * Return index by key.
     * @param key
     * @return id.
     */
    private int index(K key) {
        return key.hashCode() % data.containerSize();
    }

    @Override
    public Iterator<K> iterator() {
        return (Iterator<K>) data.iterator();
    }

    /**
     * Number of pairs.
     * @return
     */
    public int size() {
        int n = 0;
        for (Iterator it1 = iterator(); it1.hasNext();) {
            LinkedListArrayBased<Pair<K, V>> list = (LinkedListArrayBased<Pair<K, V>>) it1.next();
            for (Iterator it2 = list.iterator(); it2.hasNext(); it2.next()) {
               n++;
            }
        }
        System.out.println("Number of elements in HashTable: " + n);
        return n;
    }
}
