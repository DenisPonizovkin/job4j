package ru.job4j.utils;

public class Node<T> {
   public T value = null;
   public Node<T> next = null;

   public Node(T value) {
       this.value = value;
   }
}
