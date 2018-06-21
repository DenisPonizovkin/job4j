package ru.job4j.generic.container.tree;

import java.util.*;

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

    /**
     * Check if the tree is binary.
     * @return - true if the tree is binary.
     */
    public boolean isBinary() {
        boolean bin = true;
        for (Iterator it = iterator(); it.hasNext();) {
            Optional<Node<E>> el = findBy((E) it.next());
            if (el.get().leaves().size() > 2) {
                bin = false;
                break;
            }
        }
        return bin;
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

        private void addChilds(Node<E> node) {
            for (Node<E> child : node.leaves()) {
                nodes.offer(child);
            }
        }

        @Override
        public boolean hasNext() {
            if (nodes.size() == 1) {
               Node<E> el = nodes.poll();
               addChilds(el);
            }
            return nodes.size() > 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node<E> node = nodes.poll();
            addChilds(node);
            return node.value();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new TreeIterator<E>(root);
    }
}
