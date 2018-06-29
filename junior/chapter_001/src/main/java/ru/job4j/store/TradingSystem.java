package ru.job4j.store;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Trading system.
 * @author Denis Ponizovkin.
 * @param <E> - type of the id of emmiters.
 */
public class TradingSystem<E> {

    /**
     * Data.
     */
    HashMap<E, DOM> data;

    /**
     * Constrcutor.
     */
    public TradingSystem() {
        data = new HashMap<E, DOM>();
    }

    /**
     * Add bid.
     * @param emmiter
     * @param bid
     */
    public void add(E emmiter, Bid bid) {
        DOM dom = null;
        if (!data.containsKey(emmiter)) {
            dom = new DOM();
        } else {
            dom = data.get(emmiter);
        }
        dom.add(bid);
        data.put(emmiter, dom);
    }

    /**
     * Delete bid.
     * @param emmiter
     * @param bid
     */
    public void delete(E emmiter, Bid bid) {
        if (!data.containsKey(emmiter)) {
            throw new NoSuchElementException();
        }
        DOM dom = data.get(emmiter);
        dom.delete(bid);
        data.put(emmiter, dom);
    }

    /**
     * Show DOM of emmiter.
     * @param emmiter
     */
    public void show(E emmiter) {
        if (!data.containsKey(emmiter)) {
            throw new NoSuchElementException();
        }
        System.out.println(data.get(emmiter).toString());
    }

    public String showAsString(E emmiter) {
        if (!data.containsKey(emmiter)) {
            throw new NoSuchElementException();
        }
        return (data.get(emmiter) + "");
    }
}
