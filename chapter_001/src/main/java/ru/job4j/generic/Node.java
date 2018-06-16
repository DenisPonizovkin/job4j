package ru.job4j.generic;

public class Node<T> {
   public T value = null;
   public Node<T> next = null;

   public Node(T value) {
       this.value = value;
   }
}
