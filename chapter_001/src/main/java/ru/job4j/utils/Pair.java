package ru.job4j.utils;

/**
 * Pair.
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> {

    public K key;
    public V value;

    /**
     * Costructor.
     * @param key
     * @param value
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
