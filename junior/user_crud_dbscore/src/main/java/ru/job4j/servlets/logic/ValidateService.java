package ru.job4j.servlets.logic;

import ru.job4j.servlets.model.User;
import ru.job4j.servlets.persistent.DbStore;

import java.util.List;

public class ValidateService {

    private final static ValidateService ourInstance = new ValidateService();
    private final DbStore store = DbStore.getInstance();

    public static ValidateService getInstance() {
        return ourInstance;
    }

    private ValidateService() {
    }


    public static boolean validate(User u) {
        return false;
    }

    public boolean add(User u) {
        boolean ok = true;
        if (store.findById(u.getId()) != null) {
           ok = false;
        } else {
            store.add(u);
        }
        return ok;
    }

    public boolean update(User u) {
        boolean ok = true;
        if (store.findById(u.getId()) == null) {
            ok = false;
        } else {
            store.update(u);
        }
        return ok;
    }

    public boolean delete(User u) {
        boolean ok = true;
        if (store.findById(u.getId()) == null) {
            ok = false;
        } else {
            store.delete(u.getId());
        }
        return ok;
    }

    public List<User> findAll() {
        return store.findAll();
    }

    public User findById(int id) {
        return store.findById(id);
    }

}
