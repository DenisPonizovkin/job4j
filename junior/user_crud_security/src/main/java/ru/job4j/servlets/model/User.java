package ru.job4j.servlets.model;

import java.text.SimpleDateFormat;

public class User {

    private int id;
    private String name;
    private String login;
    private String email;
    private String password;
    private long createDate;
    private Role role;

    public boolean isEmpty() {
        return id == -1;
    }

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getFormattedCreationDate() {
       return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(createDate);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        User u = (User) o;
        return u.getId() == getId();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += id == -1 ? 0 : (int) (id ^ (id >>> 32));
        hash += name.isEmpty() ? 0 : name.hashCode();
        hash += login.isEmpty() ? 0 : login.hashCode();
        hash += email.isEmpty() ? 0 : email.hashCode();
        hash += createDate == 0 ? 0 : (int) (createDate ^ (createDate >>> 32));
        hash += role.hashCode();

        return 31 * 17 + hash;
    }
}
