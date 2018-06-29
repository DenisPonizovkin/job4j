package ru.job4j.generic;


import org.junit.Test;
import ru.job4j.utils.Node;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class NodeTest {
	boolean hasCycle(Node first) {
	    Node tortoise = first;
	    Node hare = first.next;
	    boolean is = false;
	    while (true) {
	    	if (tortoise == null || hare == null) {
	    		break;
			}
			if (tortoise == hare) {
	    		is = true;
	    		break;
			}
			tortoise = tortoise.next;
	    	hare = hare.next.next;
		}
		return is;
	}

	@Test
	public void whenTakeNameThenTreeEchoPlusName() {
		Node<Integer> first = new Node<Integer>(1);
		Node<Integer> two = new Node<Integer>(2);
		Node<Integer> third = new Node<Integer>(3);
		Node<Integer> four = new Node<Integer>(4);

		first.next = two;
		two.next = third;
		third.next = four;
		four.next = first;

		assertThat(hasCycle(first), is(true));
	}

}

