package ru.job4j.generic.container.set;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleSetBasedHashTableTest {

    private SimpleSetBasedHashTable<Integer> set;

    /**
     * Constructor.
     */
    public SimpleSetBasedHashTableTest() {
        set = new SimpleSetBasedHashTable<>();
    }

    @Test
    public void whenAddNElementsSizeIsN() {

        set.clear();

        int n = 0;
        for (int i = 0; i < n; i++) {
            set.add(i);
        }

        assertThat(set.size(), is(n));
    }

    @Test
    public void whenAskIsContainsNotContainingElementThenFalse() {

        set.clear();

        set.add(1);
        set.add(2);
        set.add(3);

        assertThat(set.contains(6), is(false));
    }

    @Test
    public void whenAskIsContainsContainingElementThenTrue() {

        set.clear();

        set.add(1);
        set.add(2);
        set.add(3);

        assertThat(set.contains(3), is(true));
    }

    @Test
    public void whenAddContaingElementThenFalse() {

        set.clear();

        set.add(1);
        set.add(2);
        set.add(3);

        assertThat(set.add(3), is(false));
    }

    @Test
    public void whenAddNotContainingElementThenTrue() {

        set.clear();

        set.add(1);
        set.add(2);
        set.add(3);

        assertThat(set.add(4), is(true));
    }

    @Test
    public void whenRemoveContainingElementThenTrue() {

        set.clear();

        set.add(1);
        set.add(200);
        set.add(30);

        assertThat(set.remove(30), is(true));
    }

    @Test
    public void whenRemoveNotContaingElementThenFalse() {

        set.clear();

        set.add(1);
        set.add(2);
        set.add(3);

        assertThat(set.remove(4), is(false));
    }

    @Test
    public void whenRemoveElementThenContainsFalse() {

        set.clear();

        set.add(1);
        set.add(2);
        set.add(3);
        assertThat(set.contains(3), is(true));
        set.remove(3);
        assertThat(set.contains(3), is(false));
    }
}
