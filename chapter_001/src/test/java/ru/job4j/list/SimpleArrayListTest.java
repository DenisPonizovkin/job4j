package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleArrayListTest {
    private SimpleArrayList<Integer> list;

    @Before
    public void beforeTest() {
        list = new SimpleArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(list.get(1), is(2));
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        assertThat(list.getSize(), is(3));
    }

    @Test
    public void whenRemoveThreeFourElementsThenGetOneTwoThree() {
        SimpleArrayList<Integer> tmp = new SimpleArrayList<Integer>();
        tmp.add(1);
        tmp.add(2);
        tmp.add(3);
        assertThat(tmp.delete(), is(3));
        assertThat(tmp.delete(), is(2));
        assertThat(tmp.delete(), is(1));
    }

    @Test(expected = NullPointerException.class)
    public void shouldNullPointerException() {
        SimpleArrayList<Integer> tmp = new SimpleArrayList<Integer>();
        tmp.add(1);
        tmp.add(2);
        tmp.add(3);
        tmp.delete();
        tmp.delete();
        tmp.delete();
        tmp.delete();
    }
}
