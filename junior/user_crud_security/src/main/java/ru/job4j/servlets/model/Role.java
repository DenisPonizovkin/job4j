package ru.job4j.servlets.model;

public class Role {

    int id;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return 31 * 17 + id;
    }

    @Override
    public boolean equals(Object o) {
        Role r = (Role) o;
        return r.getId() == this.id;
    }
}
