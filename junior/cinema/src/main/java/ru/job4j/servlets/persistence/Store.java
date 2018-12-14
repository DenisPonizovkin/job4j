package ru.job4j.servlets.persistence;

import java.util.List;

public interface Store<T> {

    public T add(T value);
    public boolean update(T value);
    public boolean delete(int id);
    public List<T> findAll();
    public T findById(int id);

}
