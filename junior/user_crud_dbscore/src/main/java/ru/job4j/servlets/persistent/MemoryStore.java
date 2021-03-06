package ru.job4j.servlets.persistent;

import ru.job4j.servlets.model.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryStore implements Store<User> {

    private static final MemoryStore ourInstance = new MemoryStore();
    private final List<User> users;

    public static MemoryStore getInstance() {
        return ourInstance;
    }

    private MemoryStore() {
        users = new CopyOnWriteArrayList<User>();
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
    public User add(User u) {
        users.add(u);
        return u;
    }

    @Override
    public boolean update(User u) {
        users.remove(u);
        users.add(u);
        return true;
    }

    @Override
    public boolean delete(int id) {
        int i = 0;
        for (User u : users) {
            if (u.getId() == id) {
                break;
            }
            i++;
        }
        boolean ok = false;
        if (i < users.size()) {
            ok = true;
            users.remove(i);
        }
        return ok;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(int id) {
        User res = null;
        for (User u : users) {
            if (u.getId() == id) {
                res = u;
                break;
            }
        }
        return res;
    }
}
