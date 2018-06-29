package ru.job4j.generic.container.map.model;

import java.util.Calendar;

public class User {

    private String name;
    private int children;
    private Calendar birthday;
    private long card = 0;
    private final int base = 17;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    public User(String name, int children, Calendar birthday, long card) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
        this.card = card;
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
    public int hashCode() {
        int hash = 0;
        hash += name.isEmpty() ? 0 : name.hashCode();
        hash += children;
        hash += birthday == null ? 0 : birthday.hashCode();
        hash += (int) (card ^ (card >>> 32));

        return 31 * base + hash;
    }

    @Override
    public boolean equals(Object o) {
        User u = (User) o;
        boolean eq = false;
        if (this == u) {
           eq = true;
        } else {
            eq = this.name.equals(u.getName())
                    && (this.getChildren() == u.getChildren())
                    && (this.birthday == u.getBirthday()
                    && (this.card == u.card));
        }
        return eq;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\''
                + ", children=" + children
                + ", birthday=" + birthday + '}';
    }

    public long getCard() {
        return card;
    }
}
