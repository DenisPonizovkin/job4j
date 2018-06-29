package ru.job4j.store;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import static ru.job4j.store.ActionType.ASK;
import static ru.job4j.store.ActionType.BID;
import static ru.job4j.store.BidType.ADD;

public class TradingSystemTest {
    @Test
    public void whenDomHasBidsWithSamePriceThenVolumesSummarize() {

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

        Bid bid2 = new Bid();
        bid2.setId((long) 2);
        bid2.setAction(BID);
        bid2.setPrice(20.0);
        bid2.setVolume((long) 15);
        bid2.setType(ADD);
        bid2.setBook((long) 1);
        sys.add(companyName, bid2);

        Bid bid3 = new Bid();
        bid3.setId((long) 3);
        bid3.setAction(ASK);
        bid3.setPrice(40.0);
        bid3.setVolume((long) 33);
        bid3.setType(ADD);
        bid3.setBook((long) 1);
        sys.add(companyName, bid3);

        Bid bid4 = new Bid();
        bid4.setId((long) 4);
        bid4.setAction(ASK);
        bid4.setPrice(10.0);
        bid4.setVolume((long) 90);
        bid4.setType(ADD);
        bid4.setBook((long) 1);
        sys.add(companyName, bid4);

        Bid bid5 = new Bid();
        bid5.setId((long) 5);
        bid5.setAction(ASK);
        bid5.setPrice(100.0);
        bid5.setVolume((long) 90);
        bid5.setType(ADD);
        bid5.setBook((long) 1);
        sys.add(companyName, bid5);

        Bid bid6 = new Bid();
        bid6.setId((long) 6);
        bid6.setAction(ASK);
        bid6.setPrice(10.0);
        bid6.setVolume((long) 100);
        bid6.setType(ADD);
        bid6.setBook((long) 1);
        sys.add(companyName, bid6);

        Bid bid7 = new Bid();
        bid7.setId((long) 7);
        bid7.setAction(ASK);
        bid7.setPrice(10.0);
        bid7.setVolume((long) 50);
        bid7.setType(ADD);
        bid7.setBook((long) 1);
        sys.add(companyName, bid7);

        sys.show(companyName);

        assertThat(sys.showAsString(companyName).contains("250\t10"), is(true));
    }
}
