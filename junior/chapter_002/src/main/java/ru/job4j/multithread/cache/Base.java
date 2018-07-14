package ru.job4j.multithread.cache;

public class Base {

    int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void update() {
        version++;
    }
}
