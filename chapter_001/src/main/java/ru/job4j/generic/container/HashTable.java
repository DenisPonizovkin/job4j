package ru.job4j.generic.container;

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
    }

    /**
     * Insert value by key.
     * @param key
     * @param value
     * @return
     */
    public boolean insert(K key, V value) {

        int id = index(key);
        boolean insert = true;
        LinkedListArrayBased<Pair<K, V>> bucket = null;
        if (data.isIdElement(id)) {
            bucket = data.get(id);
        }
        if (bucket != null) {
            for (Iterator it = bucket.iterator(); it.hasNext();) {
                Pair<K, V> pair = (Pair<K, V>) it.next();
                if (key.equals(pair.key)) {
                    pair.value = value;
                    insert = false;
                    break;
                }
            }
        }
        if (insert) {
            insert(id, new Pair<K, V>(key, value));
        }
        return insert;
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
        if (bucket != null) {
            for (Iterator it = bucket.iterator(); it.hasNext();) {
                Pair<K, V> pair = (Pair<K, V>) it.next();
                if (key.equals(pair.key)) {
                    v = pair.value;
                    break;
                }
            }
        }
        return v;
    }

    /**
     * Insert new pair.
     */
    private void insert(int id, Pair<K, V> pair) {

        /* TODO - resize */
        LinkedListArrayBased<Pair<K, V>> bucket = null;
        boolean newBucket = false;
        if (data.isIdElement(id)) {
            bucket = data.get(id);
        } else {
            bucket = new LinkedListArrayBased<Pair<K, V>>();
            newBucket = true;
        }
        bucket.add(pair);
        if (newBucket) {
            data.add(bucket);
        }
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
        return null;
    }
}
