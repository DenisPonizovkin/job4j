package ru.job4j.generic.container.tree;

import java.util.Optional;

/**
 * Simple tree realisation.
 *
 * @author Denis Ponizovkin.
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     * @param parent parent.
     * @param child child.
     * @return
     */
    boolean add(E parent, E child);

    Optional<Node<E>> findBy(E value);
}
