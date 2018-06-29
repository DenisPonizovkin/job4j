package ru.job4j.generic.container.list.linked;

import org.junit.Test;

public class LinkedListArrayBasedTest {

    @Test
    public void addValuesAndPrint() {
        LinkedListArrayBased<Integer> ll = new LinkedListArrayBased<>();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);
        ll.add(6);
        ll.print();
        ll.remove(3);
        ll.print();
    }
}
