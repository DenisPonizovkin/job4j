package ru.job4j.generic.container.tree;

import java.util.*;

public class BST<E extends Comparable<E>> implements Iterable<E> {

    /**
     * Root of the tree.
     */
    private NodeBST root;
    private NodeBST cursor;

    /**
     * Constructor.
     * @param v - value of the root.
     */
    public BST(E v) {
        root = new NodeBST(v);
        cursor = null;
    }


    @Override
    public Iterator<E> iterator() {
        return new BSTIterator<E>(root);
    }

    public void add(E child) {

        if (cursor == null) {
            cursor = root;
        }
        int cmp = cursor.getKey().compareTo(child);
        if (cmp <= 0) {
            if (cursor.getRight() == null) {
                cursor.setRight(new NodeBST(child));
                cursor = null;
            } else {
                cursor = cursor.getRight();
                add(child);
            }
        } else {
            if (cursor.getLeft() == null) {
                cursor.setLeft(new NodeBST(child));
                cursor = null;
            } else {
                cursor = cursor.getLeft();
                add(child);
            }
        }
    }

    public Optional<NodeBST<E>> find(E value) {
        Optional<NodeBST<E>> rsl = Optional.of(root);
        while (rsl.isPresent()) {
            int cmp = rsl.get().getKey().compareTo(value);
            if (cmp > 0) {
                rsl = Optional.of(rsl.get().getLeft());
            } else if (cmp < 0) {
                rsl = Optional.of(rsl.get().getRight());
            } else {
                break;
            }
        }
        return rsl;
    }


    /**
     * Number of elements in the tree.
     * @return - number of elements.
     */
    public int size() {
        int size = 0;
        for (Iterator it = iterator(); it.hasNext(); it.next()) {
           ++size;
        }
        return size;
    }

    class BSTIterator<E extends Comparable<E>> implements Iterator<E> {

        private Queue<NodeBST<E>> nodes;

        public BSTIterator(NodeBST<E> root) {
            nodes = new LinkedList<>();
            nodes.offer(root);
        }

        @Override
        public boolean hasNext() {
            return nodes.size() > 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            NodeBST<E> next = nodes.poll();
            if (next.getLeft() != null) {
                nodes.offer(next.getLeft());
            }
            if (next.getRight() != null) {
                nodes.offer(next.getRight());
            }
            return next.getKey();
        }
    }
}
