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
    public void add(Bid bid) {

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
        Bid prev = null;
        for (Iterator it = list.iterator(); it.hasNext();) {
            if (prev == null) {
                prev = (Bid) it.next();
                out.add(prefix + prev.getVolume() + "\t" + prev.getPrice());
                if (list.size() == 1) {
                    break;
                }
            }
            Bid current = (Bid) it.next();
            if (prev.getPrice().equals(current.getPrice())) {
                if (out.size() > 1) {
                    out.remove(out.size() - 1);
                }
                out.add(prefix + (prev.getVolume() + current.getVolume()) + "\t" + prev.getPrice());
            } else {
                out.add(prefix + current.getVolume() + "\t" + current.getPrice());
            }
            prev = current;
        }
        return out;
    }
}
