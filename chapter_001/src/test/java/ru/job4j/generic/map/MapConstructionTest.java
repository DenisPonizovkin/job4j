package ru.job4j.generic.map;

import org.junit.Test;
import ru.job4j.generic.container.map.model.User;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MapConstructionTest {

    @Test
    public void printWithUsersWhichHaveEqualsFileds() {
       User u1 = new User("user", 1, null);
       User u2 = new User("user", 1, null);

       Map<User, Integer> map = new HashMap<User, Integer>();
       Integer n = 1;
       map.put(u1, n++);
       map.put(u2, n++);

       for (Map.Entry<User, Integer> e: map.entrySet()) {
          System.out.println("\n" + e.getValue() + ": " + e.getKey());
       }
    }
}
