package tracker;

import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TrackerTest {

    @Test
    public void whenInitThenNoExceptionsAndConnectionIsNotNull() throws
            IOException,
            Tracker.CreateDatabaseStructoreErrors {

        Tracker t = new Tracker();
        t.init();

        assertThat(t.connectionIsNull(), is(false));
    }

    @Test
    public void whenAddItemThenFindThisItem() throws
            IOException,
            Tracker.CreateDatabaseStructoreErrors,
            Tracker.ConnectionIsNull, SQLException {

        Tracker t = new Tracker();
        t.init();

        Item i = new Item(1, "item");
        i = t.add(i);

        List<Item> items = t.findByName("item");

        assertThat(items.get(items.size() - 1).equals(i), is(true));
    }
}
