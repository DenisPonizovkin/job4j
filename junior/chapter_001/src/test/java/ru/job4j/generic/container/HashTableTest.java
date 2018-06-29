package ru.job4j.generic.container;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HashTableTest {

    @Test
    public void whenInsertValueByKeyThenGetValueByKey() {
        HashTable<Integer, String>  table = new HashTable<Integer, String>();
        Integer i = 0;
        table.insert(i, "v" + i++);
        table.insert(i, "v" + i++);
        table.insert(i, "v" + i++);
        table.insert(i, "v" + i++);
        table.insert(i, "v" + i++);
        table.insert(i, "v" + i++);
        assertThat(table.get(0), is("v0"));
        assertThat(table.get(1), is("v1"));
        assertThat(table.get(2), is("v2"));
        assertThat(table.get(3), is("v3"));
        assertThat(table.get(4), is("v4"));
        assertThat(table.get(5), is("v5"));
    }

    @Test
    public void whenRemoveValueByKeyWithCollisionsThenSizeDecrease() {
        HashTable<SimpleHashCodeForTest, String> table = new HashTable<SimpleHashCodeForTest, String>();
        SimpleHashCodeForTest k1 = new SimpleHashCodeForTest(1);
        SimpleHashCodeForTest k2 = new SimpleHashCodeForTest(1);
        SimpleHashCodeForTest k3 = new SimpleHashCodeForTest(1);
        SimpleHashCodeForTest k4 = new SimpleHashCodeForTest(2);
        SimpleHashCodeForTest k5 = new SimpleHashCodeForTest(2);
        SimpleHashCodeForTest k6 = new SimpleHashCodeForTest(2);
        SimpleHashCodeForTest k7 = new SimpleHashCodeForTest(2);

        table.insert(k1, "v1");
        table.insert(k2, "v2");
        table.insert(k3, "v3");
        table.insert(k4, "v4");
        table.insert(k5, "v5");
        table.insert(k6, "v6");
        table.insert(k7, "v7");

        int size = table.size();
        table.delete(k4);
        assertThat(table.size(), is(size - 1));
    }

    @Test
    public void whenRemoveValueByKeyThenSizeDecrease() {
        HashTable<Integer, String>  table = new HashTable<Integer, String>();
        Integer i = 0;
        table.insert(0, "v" + i++);
        table.insert(1, "v" + i++);
        table.insert(2, "v" + i++);
        table.insert(3, "v" + i++);
        table.insert(4, "v" + i++);
        table.insert(5, "v" + i++);

        int size = table.size();
        table.delete(3);
        assertThat(table.size(), is(size - 1));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenRemoveValueByKeyWithCollisionsThenGetValueByKeyIsNull() {
        HashTable<SimpleHashCodeForTest, String> table = new HashTable<SimpleHashCodeForTest, String>();
        SimpleHashCodeForTest k1 = new SimpleHashCodeForTest(1);
        SimpleHashCodeForTest k2 = new SimpleHashCodeForTest(1);
        SimpleHashCodeForTest k3 = new SimpleHashCodeForTest(1);
        SimpleHashCodeForTest k4 = new SimpleHashCodeForTest(2);
        SimpleHashCodeForTest k5 = new SimpleHashCodeForTest(2);
        SimpleHashCodeForTest k6 = new SimpleHashCodeForTest(2);
        SimpleHashCodeForTest k7 = new SimpleHashCodeForTest(2);

        table.insert(k1, "v1");
        table.insert(k2, "v2");
        table.insert(k3, "v3");
        table.insert(k4, "v4");
        table.insert(k5, "v5");
        table.insert(k6, "v6");
        table.insert(k7, "v7");

        table.delete(k4);
        table.get(k4);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenRemoveValueByKeyThenGetValueByKeyIsNull() {
        HashTable<Integer, String>  table = new HashTable<Integer, String>();
        Integer i = 0;
        table.insert(0, "v" + i++);
        table.insert(1, "v" + i++);
        table.insert(2, "v" + i++);
        table.insert(3, "v" + i++);
        table.insert(4, "v" + i++);
        table.insert(5, "v" + i++);

        table.delete(3);
        table.get(3);
    }

    @Test
    public void whenInsertValuesWithEqualsKeysThenOverwriteOldValues() {
        HashTable<Integer, String>  table = new HashTable<Integer, String>();
        Integer i = 0;
        table.insert(i, "i1");
        table.insert(i, "i2");
        assertThat(table.get(i), is("i2"));

        Integer j = 1;
        table.insert(j, "j1");
        table.insert(j, "j2");
        table.insert(j, "j3");
        table.insert(j, "j4");
        assertThat(table.get(j), is("j4"));
    }

    @Test
    public void whenInsertValuesWithEqualsKeysThenSizeNotChanges() {
        HashTable<Integer, String>  table = new HashTable<Integer, String>();
        Integer i = 0;
        table.insert(i, "i1");
        table.insert(i, "i2");
        assertThat(table.size(), is(1));
    }

    @Test
    public void whenInsertNValuesThenSizeIsN() {
        HashTable<Integer, String>  table = new HashTable<Integer, String>();
        final int n = 100;
        for (int i = 0; i < n; i++) {
           table.insert(i, "v" + i + "");
        }
        assertThat(table.size(), is(n));
    }

    class SimpleHashCodeForTest {
        private int hash = 0;

        SimpleHashCodeForTest(int n) {
            hash = n;
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }

    @Test
    public void whenInsertNValuesWithKeysWhichHashCodeEqualsThenSizeIsN() {
        HashTable<SimpleHashCodeForTest, String> table = new HashTable<SimpleHashCodeForTest, String>();
        table.insert(new SimpleHashCodeForTest(1), "v1");
        table.insert(new SimpleHashCodeForTest(1), "v2");
        table.insert(new SimpleHashCodeForTest(1), "v3");
        table.insert(new SimpleHashCodeForTest(1), "v4");
        table.insert(new SimpleHashCodeForTest(2), "v5");
        table.insert(new SimpleHashCodeForTest(2), "v6");
        table.insert(new SimpleHashCodeForTest(2), "v7");
        assertThat(table.size(), is(7));
    }

    @Test
    public void whenInsertValuesWithKeysWhichHashCodeEquaGetValues() {
        HashTable<SimpleHashCodeForTest, String> table = new HashTable<SimpleHashCodeForTest, String>();
        SimpleHashCodeForTest k1 = new SimpleHashCodeForTest(1);
        SimpleHashCodeForTest k2 = new SimpleHashCodeForTest(1);
        SimpleHashCodeForTest k3 = new SimpleHashCodeForTest(1);
        SimpleHashCodeForTest k4 = new SimpleHashCodeForTest(2);
        SimpleHashCodeForTest k5 = new SimpleHashCodeForTest(2);
        SimpleHashCodeForTest k6 = new SimpleHashCodeForTest(2);
        SimpleHashCodeForTest k7 = new SimpleHashCodeForTest(2);
        table.insert(k1, "v1");
        table.insert(k2, "v2");
        table.insert(k3, "v3");
        table.insert(k4, "v4");
        table.insert(k5, "v5");
        table.insert(k6, "v6");
        table.insert(k7, "v7");

        assertThat(table.get(k1), is("v1"));
        assertThat(table.get(k2), is("v2"));
        assertThat(table.get(k3), is("v3"));
        assertThat(table.get(k4), is("v4"));
        assertThat(table.get(k5), is("v5"));
        assertThat(table.get(k6), is("v6"));
        assertThat(table.get(k7), is("v7"));
    }
}
