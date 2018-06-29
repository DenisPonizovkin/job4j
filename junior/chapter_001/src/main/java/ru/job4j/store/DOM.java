package ru.job4j.store;

import java.util.*;

import static ru.job4j.store.ActionType.ASK;
import static ru.job4j.store.BidType.DELETE;

public class DOM {

    private List<Bid> buy;
    private List<Bid> sell;

    /**
     * Constructor.
     */
    public DOM() {
        buy = new ArrayList<Bid>();
        sell = new ArrayList<Bid>();
    }

    /**
     * Add or delete bid.
     * @param bid
     */
    public void add(Bid bidForAdding) {
        Bid bid = new Bid();
        bid.setPrice(bidForAdding.getPrice());
        bid.setId(bidForAdding.getId());
        bid.setVolume(bidForAdding.getVolume());
        bid.setBook(bidForAdding.getBook());
        bid.setType(bidForAdding.getType());
        bid.setAction(bidForAdding.getAction());
        if (bid.getType() == DELETE) {
            delete(bid);
        } else {
            if (bid.getAction() == ASK) {
                sell.add(bid);
                Collections.sort(sell, new Comparator<Bid>() {
                    @Override
                    public int compare(Bid l, Bid r) {
                        return -l.compareTo(r);
                    }
                });
            } else {
                buy.add(bid);
                Collections.sort(buy, new Comparator<Bid>() {
                    @Override
                    public int compare(Bid l, Bid r) {
                        return -l.compareTo(r);
                    }
                });
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
            Collections.sort(sell, new Comparator<Bid>() {
                @Override
                public int compare(Bid l, Bid r) {
                    return -l.compareTo(r);
                }
            });
        } else {
            buy.remove(bid);
            Collections.sort(buy, new Comparator<Bid>() {
                @Override
                public int compare(Bid l, Bid r) {
                    return -l.compareTo(r);
                }
            });
        }
    }

    @Override
    public String toString() {
        List<String> out = new ArrayList<String>();
        out.add("Продажа\tЦена\tПокупка");
        out = toString(buy, "", out);
        out = toString(sell, "\t", out);
        return String.join("\n", out);
    }

    private List<String> toString(List<Bid> list, String prefix, List<String> out) {
        if (list.size() == 1) {
            out.add(prefix + list.get(0).getVolume() + "\t" + list.get(0).getPrice());
        } else {
            Bid current = null;
            for (int i = 0; i < list.size();) {
                current = new Bid(list.get(i));
                Bid next = null;
                int j = i + 1;
                for (; j < list.size(); j++) {
                    next = new Bid(list.get(j));
                    if (!current.getPrice().equals(next.getPrice())) {
                        break;
                    }
                    current.setVolume(next.getVolume() + current.getVolume());
                }
                out.add(prefix + current.getVolume() + "\t" + current.getPrice());
                i = j;
            }
        }
        return out;
    }
}
