package ru.job4j.generic.container;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HashTableTest {

    @Test
    public void whenInsertValyeByKeyGetValueByKey() {
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

}