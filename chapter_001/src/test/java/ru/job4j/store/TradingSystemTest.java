package ru.job4j.store;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static ru.job4j.store.ActionType.*;
import static ru.job4j.store.BidType.ADD;

public class TradingSystemTest {

    @Test
    public void whenDomHasTwoBidsWithSamePriceThenVolumesSummarize() {

        TradingSystem<String> sys = new TradingSystem<>();
        String companyName = "company";
        Bid bid1 = new Bid();
        bid1.setId((long) 1);
        bid1.setBook((long) 1);
        bid1.setPrice(10.0);
        bid1.setType(ADD);
        bid1.setAction(ASK);
        bid1.setVolume((long) 10);
        sys.add(companyName, bid1);

        Bid bid2 = new Bid(bid1);
        bid2.setId((long) 2);
        bid2.setAction(BID);
        bid2.setPrice(20.0);
        bid2.setVolume((long) 15);
        sys.add(companyName, bid2);

        Bid bid3 = new Bid(bid1);
        bid3.setId((long) 3);
        bid3.setAction(ASK);
        bid3.setPrice(40.0);
        bid3.setVolume((long) 33);
        sys.add(companyName, bid3);

        Bid bid4 = new Bid(bid1);
        bid4.setId((long) 4);
        bid4.setAction(ASK);
        bid4.setPrice(10.0);
        bid4.setVolume((long) 90);
        sys.add(companyName, bid4);

        Bid bid5 = new Bid(bid1);
        bid5.setId((long) 5);
        bid5.setAction(ASK);
        bid5.setPrice(100.0);
        bid5.setVolume((long) 90);
        sys.add(companyName, bid5);

        Bid bid6 = new Bid(bid1);
        bid6.setId((long) 6);
        bid6.setAction(ASK);
        bid6.setPrice(10.0);
        bid6.setVolume((long) 100);
        sys.add(companyName, bid6);

        sys.show(companyName);

        assertThat(sys.showAsString(companyName).contains("200\t10"), is(true));
    }

}