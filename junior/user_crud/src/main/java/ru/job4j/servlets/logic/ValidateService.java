package ru.job4j.servlets.logic;

import ru.job4j.servlets.model.User;
import ru.job4j.servlets.persistent.MemoryStore;

import java.util.List;

public class ValidateService {

    private static ValidateService ourInstance = new ValidateService();
    private final MemoryStore store = MemoryStore.getInstance();

    public static ValidateService getInstance() {
        return ourInstance;
    }

    private ValidateService() {
    }


    public static boolean validate(User u) {
        return false;
    }

    public boolean add(User u) {
        return store.add(u);
    }

    public boolean update(User u) {
        return update(u);
    }

    public boolean delete(User u) {
        return store.delete(u);
    }

    public List<User> findAll() {
        return store.findAll();
    }

    public User findById(int id) {
        return store.findById(id);
    }

}
