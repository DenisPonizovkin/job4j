package ru.job4j.generic.container.set;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleSetBasedOnLinkedListTest {

    @Test
    public void whenAdd3ElementsThenSizeIs3() {
        SimpleSet<Integer> set = new SimpleSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);

        assertThat(set.size(), is(3));
    }

}
