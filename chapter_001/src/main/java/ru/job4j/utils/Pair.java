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

    @Override
    public boolean equals(Object o) {
        Pair<K, V> p = (Pair<K, V>) o;
        return ((p.key == this.key) && (p.value == this.value));
    }

    @Override
    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) + (value == null ? 0 : value.hashCode());
    }
}
