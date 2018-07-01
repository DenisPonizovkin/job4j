package ru.job4j.multithread.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class UserStorage {

    @GuardedBy("users")
    private List<User> users;

    public UserStorage() {
        users = new ArrayList<>();
    }

    public boolean add(User u) {
        synchronized (users) {
            users.add(new User(u));
        }
        return true;
    }

    public boolean update(User u) {
        boolean ok = false;
        synchronized (users) {
            if (users.contains(u)) {
                ok = true;
                System.out.println("Update " + users.get(users.indexOf(u)) + " => " + u);
                users.set(users.indexOf(u), u);
            }
        }
        return ok;
    }

    public boolean delete(User u) {
        boolean ok = false;
        synchronized (users) {
            if (users.contains(u)) {
                ok = true;
                users.remove(u);
            }
        }
        return ok;
    }

    public boolean transfer(int from, int to, int amount) {

        int fromId = -1;
        int toId = -1;
        synchronized (users) {
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);

                if (u.getId() == from) {
                    fromId = i;
                }
                if (u.getId() == to) {
                    toId = i;
                }
                if ((toId >= 0) && (fromId >= 0)) {
                    break;
                }
            }
            if ((toId >= 0) && (fromId >= 0)) {
                users.get(toId).setAmount(users.get(toId).getAmount() + amount);
                users.get(fromId).setAmount(users.get(fromId).getAmount() - amount);
            }
        }
        return (toId >= 0) && (fromId >= 0);
    }

    public User getUser(int i) {
        User u = null;
        synchronized (users) {
            for (Iterator it = users.iterator(); it.hasNext();) {
                u = (User) it.next();
                if (u.getId() == i) {
                   break;
                }
            }
        }
        return u;
    }
}
