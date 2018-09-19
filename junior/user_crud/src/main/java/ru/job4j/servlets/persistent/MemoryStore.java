package ru.job4j.servlets.persistent;

import ru.job4j.servlets.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryStore implements Store {

    private static final MemoryStore ourInstance = new MemoryStore();
    private final List<User> users = new CopyOnWriteArrayList<User>();

    public static MemoryStore getInstance() {
        return ourInstance;
    }

    private MemoryStore() {
        for (int i = 0; i < 10; i++) {
            User u = new User();
            u.setId(i);
            u.setEmail("user" + i + "@gmail.com");
            u.setLogin("login" + i);
            u.setName("user" + i);
            u.setCreateDate(System.currentTimeMillis() / 1000);
            users.add(u);
        }
    }

    @Override
    public boolean add(User u) {
        return users.add(u);
    }

    @Override
    public boolean update(User u) {
        users.remove(u);
        users.add(u);
        return true;
    }

    @Override
    public boolean delete(User rm) {
        int i = users.indexOf(rm);
        if (i >= 0) {
            users.remove(i);
        }
        return i >= 0;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(int id) {
        Optional<User> res = users.stream().filter(u -> u.getId() == id).findFirst();
        return res.get();
    }
}
