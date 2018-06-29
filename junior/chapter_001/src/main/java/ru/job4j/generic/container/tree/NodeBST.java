package ru.job4j.generic.container.tree;

public class NodeBST<E extends Comparable<E>> {

    private E key = null;
    private NodeBST<E> left;
    private NodeBST<E> right;

    public NodeBST(E value) {
        this.key = value;
    }

    public E getKey() {
        return key;
    }

    public void setKey(E key) {
        this.key = key;
    }

    public NodeBST<E> getLeft() {
        return left;
    }

    public void setLeft(NodeBST<E> left) {
        this.left = left;
    }

    public NodeBST<E> getRight() {
        return right;
    }

    public void setRight(NodeBST<E> right) {
        this.right = right;
    }
}
