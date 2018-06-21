package ru.job4j.generic.container.tree;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Petr Arsentev (mailto:parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class TreeTest {
    @Test
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenTreeIsBinThenIsBinMethodReturnTrue() {
        Tree<String> tree = new Tree<>("a");
        tree.add("a", "ab");
        tree.add("a", "ac");
        tree.add("ab", "abc");
        tree.add("ab", "abd");
        tree.add("abc", "abcd");
        tree.add("abc", "abce");
        tree.add("abd", "abcf");
        tree.add("abd", "abcg");
        tree.add("abcd", "1");
        tree.add("abcd", "2");
        tree.add("abce", "3");
        tree.add("abce", "4");
        tree.add("abcf", "5");
        tree.add("abcf", "6");
        tree.add("abcg", "7");
        tree.add("abcg", "8");
        assertThat(tree.isBinary(), is(true));
    }


    @Test
    public void whenTreeIsNotBinThenIsBinMethodReturnFalse() {
        Tree<String> tree = new Tree<>("a");
        tree.add("a", "ab");
        tree.add("a", "ac");
        tree.add("ab", "abc");
        tree.add("ab", "abd");
        tree.add("abc", "abcd");
        tree.add("abc", "abce");
        tree.add("abd", "abcf");
        tree.add("abd", "abcg");
        tree.add("abcd", "1");
        tree.add("abcd", "2");
        tree.add("abcd", "5");
        tree.add("abcd", "6");
        tree.add("abcd", "7");
        tree.add("abcd", "8");
        tree.add("abce", "3");
        tree.add("abce", "4");
        assertThat(tree.isBinary(), is(false));
    }
}
