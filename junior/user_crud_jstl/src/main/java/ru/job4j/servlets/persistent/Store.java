package ru.job4j.servlets.persistent;

import ru.job4j.servlets.model.User;

import java.util.List;

public interface Store {

    public boolean add(User u);
    public boolean update(User u);
    public boolean delete(User u);
    public List<User> findAll();
    public User findById(int id);

}
