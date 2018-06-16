package ru.job4j.generic.container.map.model;

import java.util.Calendar;

public class User {

    private String name;
    int children;
    Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public void print() {
        System.out.printf(this.toString());
    }

    @Override
    public boolean equals(User u) {
        return  this.name.equals(u.getName())
                && (this.getChildren() == u.getChildren())
                && (this.birthday == u.getBirthday());
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\''
                + ", children=" + children
                + ", birthday=" + birthday + '}';
    }
}
