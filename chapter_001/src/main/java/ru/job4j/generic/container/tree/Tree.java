package ru.job4j.generic.container.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * IMplementation of SimpleTree interface.
 *
 * @author Denis Ponizovkin.
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {

    private Node<E> root;

    public Tree(E v) {
        root = new Node<E>(v);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean ok = false;

        Optional<Node<E>> parentNode = findBy(parent);
        if (parentNode.isPresent()) {
            Node<E> childNode = new Node<>(child);
            parentNode.get().add(childNode);
        }
        return ok;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    class TreeIterator<E extends Comparable<E>> implements Iterator<E> {

        private Queue<Node<E>> nodes;

        public TreeIterator(Node<E> root) {

            nodes = new LinkedList<>();
            nodes.offer(root);
        }

        @Override
        public boolean hasNext() {
            if (nodes.size() == 1) {
               Node<E> el = nodes.poll();
               for (Node<E> child : el.leaves()) {
                   nodes.offer(child);
               }
            }
            return nodes.size() > 0;
        }

        @Override
        public E next() {
            return null;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new TreeIterator<E>(root);
    }
}
