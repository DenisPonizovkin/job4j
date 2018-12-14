package ru.job4j.servlets.service;

import java.util.List;

public interface StoreService<T> {
    public T add(T value);
    public boolean update(T value);
    public boolean delete(int id);
    public List<T> findAll();
    public T findById(int id);
}
