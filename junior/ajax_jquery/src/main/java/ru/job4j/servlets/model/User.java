package ru.job4j.servlets.model;

public class User {

    private String fname;
    private String sname;
    private String sex;
    private String desc;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        User user = (User) object;
        return java.util.Objects.equals(fname, user.fname) &&
                java.util.Objects.equals(sname, user.sname) &&
                java.util.Objects.equals(sex, user.sex) &&
                java.util.Objects.equals(desc, user.desc);
    }

    public int hashCode() {
        int hash = 0;
        hash += fname.isEmpty() ? 0 : fname.hashCode();
        hash += sname.isEmpty() ? 0 : sname.hashCode();
        hash += sex.isEmpty() ? 0 : sex.hashCode();
        hash += desc.isEmpty() ? 0 : desc.hashCode();

        return 31 * 17 + hash;
    }
}
