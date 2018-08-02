package ru.job4j.store;

import java.util.*;

import static ru.job4j.store.ActionType.ASK;
import static ru.job4j.store.ActionType.BID;
import static ru.job4j.store.BidType.DELETE;

public class DOM {

    private final TreeMap<Bid, Integer> buy  = new TreeMap<>();
    private final TreeMap<Bid, Integer> sell = new TreeMap<>();

    /**
     * Add or delete bid.
     * @param bid
     */
    public void add(Bid bidForAdding) {

        Bid bid = new Bid(bidForAdding);
        if (bid.getType() == DELETE) {
            delete(bid);
        } else {
            if (bid.getAction() == ASK) {
                sell.put(bid, sell.size() + 1);
            } else {
                buy.put(bid, buy.size() + 1);
            }
        }
    }

    /**
     * Delete bid.
     * @param bid
     */
    public void delete(Bid bid) {
        if (bid.getAction() == ASK) {
            sell.remove(bid);
        } else {
            buy.remove(bid);
        }
    }

    @Override
    public String toString() {
        List<String> out = new ArrayList<String>();
        out.add("Продажа\tЦена\tПокупка");
        out = toString(buy, "", out, BID);
        out = toString(sell, "\t", out, ASK);
        return String.join("\n", out);
    }

    private List<String> toString(TreeMap<Bid, Integer> mapSource, String prefix, List<String> out, ActionType type) {
        TreeMap<Bid, Integer> map = new TreeMap<Bid, Integer>();
        map.putAll(mapSource);
        Bid current = null;
        for (int i = 0; i < mapSource.size();) {
            Map.Entry<Bid, Integer> e1 = map.firstEntry();
            current = new Bid(e1.getKey());
            map.remove(e1.getKey(), e1.getValue());
            i++;

            Bid next = null;
            int n = map.size();
            for (int j = 0; j < n; j++) {
                Map.Entry<Bid, Integer> e2 = map.firstEntry();
                next = new Bid(e2.getKey());
                if (!current.getPrice().equals(next.getPrice())) {
                    break;
                }
                current.setVolume(next.getVolume() + current.getVolume());
                map.remove(e2.getKey(), e2.getValue());
                i++;
            }
            if (type == BID) {
                out.add(prefix + current.getVolume() + "\t" + current.getPrice());
            } else {
                out.add(prefix + current.getPrice() + "\t" + current.getVolume());
            }
        }
        //}
        return out;
    }
}
