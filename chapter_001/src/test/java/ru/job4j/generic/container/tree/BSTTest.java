package ru.job4j.generic.container.tree;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BSTTest {

    @Test
    public void whenAdd6ElementsThenSizeIs6() {
        BST<Integer> tree = new BST(10);
        tree.add(1);
        tree.add(20);
        tree.add(5);
        tree.add(30);
        tree.add(15);

        assertThat(tree.size(), is(6));
    }

    @Test
    public void whenAddlementsThenFindElement() {
        BST<Integer> tree = new BST(10);
        tree.add(1);
        tree.add(20);
        tree.add(5);
        tree.add(30);
        tree.add(15);

        assertThat(tree.find(30).isPresent(), is(true));
    }

}