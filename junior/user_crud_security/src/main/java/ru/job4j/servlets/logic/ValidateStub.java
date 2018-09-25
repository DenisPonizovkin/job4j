package ru.job4j.servlets.logic;

import ru.job4j.servlets.model.Role;
import ru.job4j.servlets.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidateStub extends ValidateService {

    private final Map<Integer, User> store = new HashMap<>();

    public ValidateStub() {
        super();
        User u = new User();
        u.setId(1);
        u.setName("admin");
        u.setLogin("admin");
        u.setEmail("admin");
        u.setPassword("admin");
        Role r = new Role(1, "admin");
        u.setRole(r);
        store.put(1, u);
    }

    @Override
    public boolean add(User user) {
        this.store.put(user.getId(), user);
        return true;
    }

    @Override
    public boolean update(User u) {
        User old = findById(u.getId());
        old = u;
        store.put(u.getId(), old);
        return true;
    }

    @Override
    public boolean delete(User u) {
        store.remove(store.get(u.getId()));
        return true;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<User>(this.store.values());
    }

    @Override
    public User findById(int id) {
        User u = null;
        try {
            u = store.entrySet().stream()
                    .filter(x -> x.getValue().getId() == id)
                    .map(x -> x.getValue())
                    .collect(Collectors.toList()).get(0);
        } catch (Exception e) { }
        return u;
    }

    @Override
    public boolean isCredentials(String login, String password) {
        User u = findByLogin(login);
        return u.getPassword().equals(password);
    }

    @Override
    public User findByLogin(String login) {
        User u = null;
        try {
            u = store.entrySet().stream()
                    .filter(x -> x.getValue().getLogin().equals(login))
                    .map(x -> x.getValue())
                    .collect(Collectors.toList()).get(0);
        } catch (Exception e) { }
        return u;
    }

    @Override
    public Role getRoleByName(String role) {
        if (role.equals("admin")) {
           return new Role(1, "admin");
        } else {
           return new Role(2, "user");
        }
    }

    @Override
    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }
}
