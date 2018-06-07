package ru.job4j.generic.store.storage;

import ru.job4j.generic.array.simple.SimpleArray;
import ru.job4j.generic.store.model.Base;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstractStorage<T extends Base> implements Storage<T> {

    private SimpleArray<T> data;

    @Override
    public void add(T model) {
        data.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        boolean isOk = true;
        try {
            data.set(id2position(id), model);
        } catch (Exception e) {
           isOk = false;
        }
        return isOk;
    }

    @Override
    public boolean delete(String id) {
        boolean isOk = true;
        try {
            data.delete(id2position(id));
        } catch (Exception e) {
            isOk = false;
        }
        return isOk;
    }

    @Override
    public T findById(String id) {
        int i = id2position(id);
        return data.get(i);
    }

    private int id2position(String id) {
        int i = 0;
        for (Iterator<T> it = data.iterator(); it.hasNext(); i++) {
            Base elem = (Base) it.next();
            if (elem.getId().equals(id)) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }
}
