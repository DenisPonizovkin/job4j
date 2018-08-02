package tracker;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TrackerTest {

    //@Test
    public void whenInitThenNoExceptionsAndConnectionIsNotNull() throws
            IOException {

        Tracker t = new Tracker();
        t.init();

        assertThat(t.connectionIsNull(), is(false));
    }

    //@Test
    public void whenAddItemThenFindThisItem() throws
            IOException, SQLException {

        Tracker t = new Tracker();
        t.init();

        Item i = new Item(1, "item");
        i = t.add(i);

        List<Item> items = t.findByName("item");

        assertThat(items.get(items.size() - 1).equals(i), is(true));
    }

    //@Test
    public void whenUpdateItemThenItemUpdated() throws
            IOException, SQLException {

        Tracker t = new Tracker();
        t.init();

        Item i1 = new Item(1, "item");
        i1 = t.add(i1);
        i1.setName("change");
        t.replace(i1.getId(), i1);

        Item i2 = t.findById(i1.getId());

        assertThat(i2.getName(), is("change"));
    }

    //@Test
    public void whenDeleteNItemsThenNumberOfItemsDecreaseByN() throws
            IOException, SQLException {

        Tracker t = new Tracker();
        t.init();

        int n = t.findAll().size();
        int id = 0;
        for (int i = 0; i < 10; i++) {
            id = t.add(new Item(1, "item")).getId();
        }
        for (int j = 0; j < 5; j++) {
            t.delete(id - j);
        }
        assertThat(t.findAll().size(), is(n + 5));
    }
}
