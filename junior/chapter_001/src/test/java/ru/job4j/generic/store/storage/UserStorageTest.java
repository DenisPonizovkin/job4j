package ru.job4j.generic.store.storage;

import ru.job4j.generic.store.model.Role;
import ru.job4j.generic.store.model.User;

import static org.junit.Assert.*;

public class UserStorageTest {

    public void testType() {
        UserStorage store = new UserStorage();
        User u = new User("1");
        Role r = new Role("2");
    }

}

