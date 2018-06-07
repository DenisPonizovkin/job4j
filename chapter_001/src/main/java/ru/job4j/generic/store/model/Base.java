package ru.job4j.generic.store.model;

/**
 * Base class for models with getId() method.
 *
 * @author Denis Ponizovkin
 */
public abstract class Base {
    private final String id;

    protected Base(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
